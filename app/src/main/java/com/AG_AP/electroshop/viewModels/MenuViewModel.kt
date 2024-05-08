package com.AG_AP.electroshop.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.AG_AP.electroshop.endpoints.models.activity.Activity
import com.AG_AP.electroshop.endpoints.models.activity.PostActivity
import com.AG_AP.electroshop.endpoints.models.businessPartners.BusinessPartners
import com.AG_AP.electroshop.endpoints.models.businessPartners.PostBusinesspartner
import com.AG_AP.electroshop.endpoints.models.login.Login
import com.AG_AP.electroshop.endpoints.models.orders.Orders
import com.AG_AP.electroshop.endpoints.models.orders.post.DocumentLine
import com.AG_AP.electroshop.endpoints.models.orders.post.PostOrder
import com.AG_AP.electroshop.endpoints.models.purchaseOrders.PurchaseOrders
import com.AG_AP.electroshop.endpoints.objects.ActivityObj
import com.AG_AP.electroshop.endpoints.objects.BusinessPartnersObj
import com.AG_AP.electroshop.endpoints.objects.LoginObj
import com.AG_AP.electroshop.endpoints.objects.OrdersObj
import com.AG_AP.electroshop.endpoints.objects.PurchaseOrdersObj
import com.AG_AP.electroshop.firebase.ActivityCRUD
import com.AG_AP.electroshop.firebase.BusinessPartnerCRUD
import com.AG_AP.electroshop.firebase.OrderCRUD
import com.AG_AP.electroshop.firebase.PurchaseOrderCRUD
import com.AG_AP.electroshop.firebase.models.BusinessPartner
import com.AG_AP.electroshop.firebase.models.DocumentLineFireBase
import com.AG_AP.electroshop.firebase.models.OrderFireBase
import com.AG_AP.electroshop.functions.Config
import com.AG_AP.electroshop.functions.ListCheckTotal
import com.AG_AP.electroshop.functions.SessionObj
import com.AG_AP.electroshop.uiState.MenuUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

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
                                TaxDate = x.TaxDate.plus("T00:00:00Z")
                            )
                            OrdersObj.postOrders(Config.rulUse,postOrder)
                            if(x.idFireBase !=null){
                                OrderCRUD.deleteObjectById(x.idFireBase)
                            }
                        }
                    }
                }
                viewModelScope.launch(Dispatchers.IO) {
                    val orders : Orders? = OrdersObj.getOrders(Config.rulUse)
                    if(orders is Orders){
                        orders.value.forEach{element ->
                            OrderCRUD.deleteObjectById(element.DocNum.toString())
                        }
                        orders.value.forEach{element ->
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
                                true
                            )
                            OrderCRUD.insert(orderInsert)
                        }
                        ListCheckTotal.addInfo("Pedido cliente actualizadas")
                        if(bol){
                            LoginObj.logout(Config.rulUse)
                            _uiState.update { currentState -> currentState.copy(
                                checkProgresCircular = false
                            ) }
                        }
                    }

                    Log.e("sync","order sync")
                }
            }
        }
    }

    fun upPurchaseOrders(bol: Boolean) {
        PurchaseOrderCRUD.getAllObject { list ->
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
                                TaxDate = x.TaxDate.plus("T00:00:00Z")
                            )
                            PurchaseOrdersObj.postPurchaseOrders(Config.rulUse,postOrder)
                            if(x.idFireBase !=null){
                                PurchaseOrderCRUD.deleteObjectById(x.idFireBase)
                            }
                        }
                    }
                }
                viewModelScope.launch(Dispatchers.IO) {
                    val data : PurchaseOrders? = PurchaseOrdersObj.getPurchaseOrders(Config.rulUse)
                    if(data is PurchaseOrders){
                        data.value.forEach{element ->
                            PurchaseOrderCRUD.deleteObjectById(element.DocNum.toString())
                        }
                        data.value.forEach{element ->
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
                                true
                            )
                            PurchaseOrderCRUD.insert(orderInsert)
                        }
                        ListCheckTotal.addInfo("Pedido compra actualizadas")
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

    fun upBusinessPartners(bol: Boolean) {
        viewModelScope.launch() {
            BusinessPartnerCRUD.getAllObject { list ->
                viewModelScope.launch() {
                    val dataLogin = Login(Config.dataBase, Config.password, Config.login)
                    if(bol){
                        LoginObj.loginAcessTwoversion(dataLogin, Config.rulUse)
                    }
                    if (list != null) {
                        val listAux = list as MutableList<BusinessPartner>
                        for (x in listAux) {
                            if (!x.SAP) {
                                var CardTypeAux:String=""
                                var SeriesAux:Int=0
                                when(x.CardType){
                                    // "value" : "Invalid item name '' in Enum 'BoCardTypes'. The valid names are: 'cCustomer'-'C', 'cSupplier'-'S', 'cLid'-'L'"
                                    "Cliente"-> {
                                        CardTypeAux= "cCustomer"
                                        SeriesAux=71
                                    }
                                    "Proveedor"-> {
                                        CardTypeAux= "cSupplier"
                                        SeriesAux=72
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
                                if(x.idFireBase !=null){
                                    BusinessPartnerCRUD.deleteObjectById(x.idFireBase)
                                }
                            }
                        }
                    }
                    viewModelScope.launch(Dispatchers.IO) {
                        val BusinessPartners = BusinessPartnersObj.getBusinessPartners(Config.rulUse)
                        if(BusinessPartners is BusinessPartners){
                            BusinessPartners.value.forEach { element->
                                BusinessPartnerCRUD.deleteObjectById(element.CardCode)
                            }

                            BusinessPartners.value.forEach { element ->
                                var email:String =""
                                var phone1:String =""
                                if(!element.Phone1.isNullOrEmpty()){
                                    phone1 = element.Phone1
                                }
                                if(!element.EmailAddress.isNullOrEmpty()){
                                    email = element.EmailAddress
                                }

                                BusinessPartnerCRUD.insert(BusinessPartner(
                                    "",
                                    element.CardCode,
                                    element.CardType,
                                    element.CardName,
                                    phone1,
                                    email,
                                    true
                                ))
                            }
                            ListCheckTotal.addInfo("Clientes actualizadas")
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
    }

    fun upItems() {
        Log.e("MenuViewModel", "upItems() method is not yet implemented")
        // Aquí deberías implementar la lógica para actualizar los ítems
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
                                ActivityCRUD.deleteActivityById(x.idFireBase)
                            }
                        }
                    }
                    viewModelScope.launch(Dispatchers.IO) {
                        val activities : Activity? = ActivityObj.getActivities(Config.rulUse)
                        if(activities is Activity){
                            activities.value.forEach{element ->
                                ActivityCRUD.deleteActivityById(element.ActivityCode.toString())
                            }
                            activities.value.forEach{element ->
                                val activity : com.AG_AP.electroshop.firebase.models.Activity = com.AG_AP.electroshop.firebase.models.Activity(
                                    "",
                                    element.Notes ?: "",
                                    element.ActivityDate ?: "",
                                    element.ActivityTime ?: "",
                                    element.CardCode ?: "",
                                    element.EndTime ?: "",
                                    element.Activity ?: "",
                                    element.Notes ?: "",//
                                    element.ActivityCode.toString() ?: "",
                                    element.Priority ?: "",
                                    element.U_SEIPEDIDOCOMPRAS ?: 0,
                                    element.U_SEIPEDIDOCLIENTE ?: 0,
                                    true
                                )
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
                upPurchaseOrders(false)
            }
            viewModelScope.launch(Dispatchers.IO) {
                upOrder(false)
            }
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