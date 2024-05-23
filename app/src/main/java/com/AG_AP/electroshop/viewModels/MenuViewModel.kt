package com.AG_AP.electroshop.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.AG_AP.electroshop.endpoints.models.activity.Activity
import com.AG_AP.electroshop.endpoints.models.activity.PostActivity
import com.AG_AP.electroshop.endpoints.models.businessPartners.PostBusinesspartner
import com.AG_AP.electroshop.endpoints.models.item.getItems.GetItems
import com.AG_AP.electroshop.endpoints.models.item.postItems.PostItem
import com.AG_AP.electroshop.endpoints.models.login.Login
import com.AG_AP.electroshop.endpoints.models.orders.post.DocumentLine
import com.AG_AP.electroshop.endpoints.models.orders.post.PostOrder
import com.AG_AP.electroshop.endpoints.objects.ActivityObj
import com.AG_AP.electroshop.endpoints.objects.BusinessPartnersObj
import com.AG_AP.electroshop.endpoints.objects.ItemObj
import com.AG_AP.electroshop.endpoints.objects.LoginObj
import com.AG_AP.electroshop.endpoints.objects.OrdersObj
import com.AG_AP.electroshop.endpoints.objects.PurchaseOrdersObj
import com.AG_AP.electroshop.firebase.ActivityCRUD
import com.AG_AP.electroshop.firebase.BusinessPartnerCRUD
import com.AG_AP.electroshop.firebase.ItemCRUD
import com.AG_AP.electroshop.firebase.OrderCRUD
import com.AG_AP.electroshop.firebase.models.BusinessPartner
import com.AG_AP.electroshop.firebase.models.Item
import com.AG_AP.electroshop.firebase.models.ItemType
import com.AG_AP.electroshop.firebase.models.OrderFireBase
import com.AG_AP.electroshop.firebase.models.Price
import com.AG_AP.electroshop.functions.Config
import com.AG_AP.electroshop.functions.ListCheckTotal
import com.AG_AP.electroshop.functions.SessionObj
import com.AG_AP.electroshop.nav.Routes
import com.AG_AP.electroshop.uiState.MenuUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MenuViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MenuUiState())
    val uiState: StateFlow<MenuUiState> = _uiState.asStateFlow()

    init {
        _uiState.update { currentState -> currentState.copy(
            username = SessionObj.name,
            articulo= SessionObj.articulo,
            actividad = SessionObj.actividad,
            pedidoCL= SessionObj.pedidoCL,
            pedidoCO= SessionObj.pedidoCO,
        ) }
    }

    fun closeSession(navController: NavHostController) {
        SessionObj.reset()
        navController.navigate(route = Routes.ScreenLogin.route)
    }

    fun viewEnd(navController: NavHostController) {
        navController.navigate(route = Routes.ScreenLogin.route)
    }

    fun closedDialog() {
        _uiState.update { currentState -> currentState.copy(
            dialog = false
        ) }
    }

    fun showDialog(textDialog: String) {
        _uiState.update { currentState -> currentState.copy(
            InfoDialog = textDialog,
            dialog = true
        ) }
    }

    fun upOrder(bol: Boolean) {
        OrderCRUD.getAllObject { list ->
            viewModelScope.launch() {
                val dataLogin = Login(Config.dataBase, Config.password, Config.login)
                if(bol){
                    LoginObj.loginAcessTwoversion(dataLogin, Config.rulUse)
                }
                if (list != null) {
                    val listAux = list as MutableList<OrderFireBase>
                    for (x in listAux) {
                        if (!x.SAP) {
                            var documentLineAux:MutableList<DocumentLine> = mutableListOf<DocumentLine>()
                            var auxNum:Int =0
                            for(y in x.DocumentLines){
                                documentLineAux.add(
                                    auxNum,
                                    DocumentLine(
                                    y.DiscountPercent, y.ItemCode, y.LineNum,y.Price,y.Quantity.toInt()
                                )
                                )
                                auxNum++
                            }
                            // Crear una instancia de PostOrder
                            val postOrder = PostOrder(
                                CardCode = x.CardCode,
                                CardName = x.CardName,
                                DiscountPercent = x.DiscountPercent,
                                DocDate = x.DocDate.plus("T00:00:00Z"),
                                DocDueDate = x.DocDueDate.plus("T00:00:00Z"),
                                DocNum = x.DocNum,
                                DocumentLines = documentLineAux,
                                TaxDate = x.TaxDate.plus("T00:00:00Z"),
                                SalesPersonCode = x.SalesPersonCode
                            )
                            OrdersObj.postOrders(Config.rulUse,postOrder)
                            if(x.idFireBase !=null){
                                OrderCRUD.deleteObjectById(x.idFireBase!!)
                            }
                        }
                    }
                    ListCheckTotal.addInfo("Pedido cliente actualizadas")
                    if(bol){
                        LoginObj.logout(Config.rulUse)
                        _uiState.update { currentState -> currentState.copy(
                            checkProgresCircular = false
                        ) }
                    }
                }
                viewModelScope.launch(Dispatchers.IO) {
                    /*var listOrderSAP: MutableList<com.AG_AP.electroshop.endpoints.models.orders.Value> =
                        mutableListOf()
                    var checkSAP: Boolean = false
                    var orders : Orders? = OrdersObj.getOrders(Config.rulUse)
                    while (!checkSAP) {
                        if (orders != null) {
                            if (orders.odataNextLink.isNullOrEmpty()) {
                                orders.value.forEach {
                                    listOrderSAP += it
                                }
                                checkSAP=true
                            }else{
                                orders.value.forEach {
                                    listOrderSAP += it
                                }
                                val num :List<String> = orders.odataNextLink.split("=")
                                orders =OrdersObj.getOrdersExtenExten(Config.rulUse,num[1].toInt())
                            }
                        }
                    }

                    listOrderSAP.forEach{element ->
                            OrderCRUD.deleteObjectById(element.DocNum.toString())
                        }
                    listOrderSAP.forEach{element ->
                            //lista de precios
                            val documentList: MutableList<DocumentLineFireBase> = mutableListOf()
                            element.DocumentLines.forEachIndexed { index, it ->
                                documentList.add(
                                    index,
                                    DocumentLineFireBase(
                                        it.ItemCode,
                                        it.ItemDescription,
                                        it.Quantity,
                                        it.DiscountPercent,
                                        it.LineNum,
                                        it.Price
                                    )
                                )
                            }
                            val orderInsert : OrderFireBase = OrderFireBase(
                                "",
                                element.DocNum,
                                element.CardCode,
                                element.CardName,
                                element.DocDate,
                                element.DocDueDate,
                                element.TaxDate,
                                element.DiscountPercent,
                                documentList,
                                true,
                                element.SalesPersonCode
                            )
                            OrderCRUD.insert(orderInsert)
                        }*/
                }
            }
        }
    }


    fun upBusinessPartners(bol: Boolean) {
        viewModelScope.launch() {
            BusinessPartnerCRUD.getAllObject { list ->
                viewModelScope.launch() {
                    val dataLogin = Login(Config.dataBase, Config.password, Config.login)
                    if (bol) {
                        LoginObj.loginAcessTwoversion(dataLogin, Config.rulUse)
                    }
                    if (list != null) {
                        val listAux = list as MutableList<BusinessPartner>
                        for (x in listAux) {
                            if (!x.SAP) {
                                var CardTypeAux: String = ""
                                var SeriesAux: Int = 0
                                when (x.CardType) {
                                    // "value" : "Invalid item name '' in Enum 'BoCardTypes'. The valid names are: 'cCustomer'-'C', 'cSupplier'-'S', 'cLid'-'L'"
                                    "Cliente" -> {
                                        CardTypeAux = "cCustomer"
                                        SeriesAux = 71
                                    }

                                    "Proveedor" -> {
                                        CardTypeAux = "cSupplier"
                                        SeriesAux = 72
                                    }
                                }


                                val data: PostBusinesspartner = PostBusinesspartner(
                                    x.CardName,
                                    CardTypeAux,
                                    x.Cellular,
                                    x.EmailAddress,
                                    SeriesAux
                                )
                                BusinessPartnersObj.postBusinessPartners(Config.rulUse, data)
                                if (x.idFireBase != null) {
                                    BusinessPartnerCRUD.deleteObjectById(x.idFireBase!!)
                                }
                            }
                        }
                    }
                    viewModelScope.launch(Dispatchers.IO) {
                        var listBusinessPartnerSAP: MutableList<com.AG_AP.electroshop.endpoints.models.businessPartners.Value> =
                            mutableListOf()
                        var checkSAP: Boolean = false
                        var BusinessPartners =
                            BusinessPartnersObj.getBusinessPartners(Config.rulUse)
                        while (!checkSAP) {
                            if (BusinessPartners != null) {
                                if (BusinessPartners.odataNextLink.isNullOrEmpty()) {
                                    BusinessPartners.value.forEach {
                                        listBusinessPartnerSAP += it
                                    }
                                    checkSAP = true
                                } else {
                                    BusinessPartners.value.forEach {
                                        listBusinessPartnerSAP += it
                                    }
                                    val num: List<String> =
                                        BusinessPartners.odataNextLink.split("=")
                                    BusinessPartners = BusinessPartnersObj.getBusinessPartnersExten(
                                        Config.rulUse,
                                        num[1].toInt()
                                    )
                                }
                            }
                        }
                        listBusinessPartnerSAP.forEach { element ->
                            BusinessPartnerCRUD.deleteObjectById(element.CardCode)
                        }

                        listBusinessPartnerSAP.forEach { element ->
                            var email: String = ""
                            var phone1: String = ""
                            if (!element.Phone1.isNullOrEmpty()) {
                                phone1 = element.Phone1
                            }
                            if (!element.EmailAddress.isNullOrEmpty()) {
                                email = element.EmailAddress
                            }
                            val bp = BusinessPartner().apply {
                                ""
                                element.CardCode
                                element.CardType
                                element.CardName
                                phone1
                                email
                                true
                            }
                            BusinessPartnerCRUD.insert(
                                bp
                            )
                        }
                        ListCheckTotal.addInfo("Clientes actualizadas")
                        if (bol) {
                            LoginObj.logout(Config.rulUse)
                            _uiState.update { currentState ->
                                currentState.copy(
                                    checkProgresCircular = false
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun upItems(bol: Boolean) {
        viewModelScope.launch() {
            ItemCRUD.getAllItems { list ->
                //TODO queda todo sobre las subidas de los item
                //TODO
                viewModelScope.launch() {
                    val dataLogin = Login(Config.dataBase, Config.password, Config.login)
                    if (bol) {
                        LoginObj.loginAcessTwoversion(dataLogin, Config.rulUse)
                    }
                    if (list != null) {
                        val listAux = list as MutableList<Item>
                        for (x in listAux) {
                            if (!x.SAP) {
                                //rellenar item
                                val listPrice: MutableList<com.AG_AP.electroshop.endpoints.models.item.postItems.ItemPrice> = mutableListOf()
                                x.itemPrice?.forEachIndexed { index, itemPrice ->
                                    listPrice.add(
                                        index,
                                        com.AG_AP.electroshop.endpoints.models.item.postItems.ItemPrice(
                                            itemPrice.currency ?: "",
                                            itemPrice.price ?: 0,
                                            itemPrice.priceList ?: 0,
                                        )
                                    )
                                }
                                //TODO
                                val item : PostItem = PostItem(
                                    73,
                                    x.autoCreateSerialNumbersOnRelease ?: "N",
                                    x.itemName,
                                    listPrice.toList(),
                                    x.itemType.toString(),
                                    x.manageSerialNumbers ?: "N"
                                )

                                ItemObj.postItems(Config.rulUse, item)
                                if (x.idFireBase != null) {
                                    ItemCRUD.deleteItemById(x.idFireBase!!)
                                }
                            }
                        }
                    }
                    viewModelScope.launch(Dispatchers.IO) {
                        var listItemSAP: MutableList<com.AG_AP.electroshop.endpoints.models.item.getItems.Value> =
                            mutableListOf()
                        var checkSAP: Boolean = false
                        var items : GetItems? = ItemObj.getItems(Config.rulUse)
                        while (!checkSAP) {
                            if (items != null) {
                                if (items.odataNextLink.isNullOrEmpty()) {
                                    items.value.forEach {
                                        listItemSAP += it
                                    }
                                    checkSAP=true
                                }else{
                                    items.value.forEach {
                                        listItemSAP += it
                                    }
                                    val num :List<String> = items.odataNextLink.split("=")
                                    items =ItemObj.getItemsExten(Config.rulUse,num[1].toInt())
                                }
                            }
                        }
                        listItemSAP.forEach{element ->
                            ItemCRUD.deleteItemById(element.ItemCode.toString())
                        }
                        listItemSAP.forEach{element ->
                            //lista de precios
                            val listPrice: MutableList<Price> = mutableListOf()
                            element.ItemPrices.forEachIndexed { index, itemPrice ->
                                val price = Price().apply {
                                    itemPrice.PriceList ?:0
                                    itemPrice.Price ?:0.0F
                                    itemPrice.Currency ?:""
                                    true
                                }
                                listPrice.add(
                                    index,
                                    price
                                )
                            }
                            val item : Item = Item().apply {
                                ""
                                element.ItemCode ?: ""
                                element.ItemName ?: ""
                                ItemType.Articulo
                                element.Mainsupplier ?: ""
                                listPrice.toList()
                                element.ManageSerialNumbers ?: ""
                                element.AutoCreateSerialNumbersOnRelease ?: ""
                                true
                            }
                            ItemCRUD.insertItem(item)
                        }
                        ListCheckTotal.addInfo("Artículos actualizados")
                        if (bol) {
                            LoginObj.logout(Config.rulUse)
                            _uiState.update { currentState ->
                                currentState.copy(
                                    checkProgresCircular = false
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun upActivities(bol: Boolean) {
        viewModelScope.launch() {
            ActivityCRUD.getAllActivity { list ->
                viewModelScope.launch() {
                    val dataLogin = Login(Config.dataBase, Config.password, Config.login)
                    if(bol){
                        LoginObj.loginAcessTwoversion(dataLogin, Config.rulUse)
                    }
                    for (x in list) {
                        if (!x.SAP) {

                            val data: PostActivity = PostActivity(
                                x.Action,
                                x.ActivityDate,
                                x.ActivityTime,
                                x.CardCode,
                                x.EndTime,
                                x.nota,
                                x.Priority,
                                x.U_SEIPEDIDOCLIENTE ?: 0,
                                x.U_SEIPEDIDOCOMPRAS ?: 0
                            )
                            ActivityObj.PostActivities(Config.rulUse, data)
                            //TODO
                            if(x.idFireBase !=null){
                                ActivityCRUD.deleteActivityById(x.idFireBase!!)
                            }
                        }
                    }
                    viewModelScope.launch(Dispatchers.IO) {
                        var listActivitySAP: MutableList<com.AG_AP.electroshop.endpoints.models.activity.Value> =
                            mutableListOf()
                        var checkSAP: Boolean = false
                        var activities: Activity? = ActivityObj.getActivities(Config.rulUse)
                        while (!checkSAP) {
                            if (activities != null) {
                                if (activities.odataNextLink.isNullOrEmpty()) {
                                    activities.value.forEach {
                                        if (it is com.AG_AP.electroshop.endpoints.models.activity.Value) {
                                            listActivitySAP += it
                                        }
                                    }
                                    checkSAP=true
                                }else{
                                    activities.value.forEach {
                                        listActivitySAP += it
                                    }
                                    val num :List<String> = activities.odataNextLink.split("=")
                                    activities =ActivityObj.getActivitiesExten(Config.rulUse,num[1].toInt())
                                }
                            }
                        }
                        listActivitySAP.forEach{element ->
                                ActivityCRUD.deleteActivityById(element.ActivityCode.toString())
                            }
                        listActivitySAP.forEach{element ->
                                val activity : com.AG_AP.electroshop.firebase.models.Activity = com.AG_AP.electroshop.firebase.models.Activity().apply {
                                    ""
                                    element.Notes ?: ""
                                    element.ActivityDate ?: ""
                                    element.ActivityTime ?: ""
                                    element.CardCode ?: ""
                                    element.EndTime ?: ""
                                    element.Activity ?: ""
                                    element.Notes ?: ""
                                    element.ActivityCode.toString() ?: ""
                                    element.Priority ?: ""
                                    element.U_SEIPEDIDOCOMPRAS ?: 0
                                    element.U_SEIPEDIDOCLIENTE ?: 0
                                    true
                                }
                                ActivityCRUD.insertActivity(activity)
                            }
                            ListCheckTotal.addInfo("Actividades actualizadas")
                            if(bol){
                                LoginObj.logout(Config.rulUse)
                                _uiState.update { currentState -> currentState.copy(
                                    checkProgresCircular = false
                                ) }
                            }
                    }
                }
            }
        }

    }

    fun changeCheckProgresCircular(){
        _uiState.update { currentState -> currentState.copy(
            checkProgresCircular = true,
            TextOrList = true
        ) }
    }

    fun upTotal() {
        _uiState.update { currentState -> currentState.copy(
            TextOrList = false
        ) }
        viewModelScope.launch() {
            ListCheckTotal.resetList()
            val dataLogin = Login(Config.dataBase, Config.password, Config.login)
            LoginObj.loginAcessTwoversion(dataLogin, Config.rulUse)
            viewModelScope.launch(Dispatchers.IO) {
                upActivities(false)
            }
            viewModelScope.launch(Dispatchers.IO) {
                upActivities(false)
            }
            viewModelScope.launch(Dispatchers.IO) {
                var aux = true
                while (aux) {
                    if (ListCheckTotal.getList().size == 4) {
                        _uiState.update { currentState ->
                            currentState.copy(
                                checkProgresCircular = false
                            )
                        }
                        aux = false
                    }
                }
                LoginObj.logout(Config.rulUse)
            }
        }
    }

}