package com.AG_AP.electroshop.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.AG_AP.electroshop.endpoints.models.activity.Activity
import com.AG_AP.electroshop.endpoints.models.item.getItems.GetItems
import com.AG_AP.electroshop.endpoints.models.login.Login
import com.AG_AP.electroshop.endpoints.models.orders.Orders
import com.AG_AP.electroshop.endpoints.models.priceList.PriceList
import com.AG_AP.electroshop.endpoints.models.purchaseOrders.PurchaseOrders
import com.AG_AP.electroshop.endpoints.objects.ActivityObj
import com.AG_AP.electroshop.endpoints.objects.BusinessPartnersObj
import com.AG_AP.electroshop.endpoints.objects.ItemObj
import com.AG_AP.electroshop.endpoints.objects.LoginObj
import com.AG_AP.electroshop.endpoints.objects.OrdersObj
import com.AG_AP.electroshop.endpoints.objects.PriceListObj
import com.AG_AP.electroshop.endpoints.objects.PurchaseOrdersObj
import com.AG_AP.electroshop.endpoints.udo.models.CreateField
import com.AG_AP.electroshop.endpoints.udo.models.createUdo.CreateUdo
import com.AG_AP.electroshop.endpoints.udo.models.createUdo.UserObjectMDFindColumn
import com.AG_AP.electroshop.endpoints.udo.models.createUdo.UserObjectMDFormColumn
import com.AG_AP.electroshop.endpoints.udo.models.createUserUDO.CreateUserUDO
import com.AG_AP.electroshop.endpoints.udo.models.getUserUdo.SeiConfigUser
import com.AG_AP.electroshop.endpoints.udo.objects.UDOobj
import com.AG_AP.electroshop.firebase.ActivityCRUD
import com.AG_AP.electroshop.firebase.BusinessPartnerCRUD
import com.AG_AP.electroshop.firebase.ItemCRUD
import com.AG_AP.electroshop.firebase.OrderCRUD
import com.AG_AP.electroshop.firebase.PriceListCRUD
import com.AG_AP.electroshop.firebase.PurchaseOrderCRUD
import com.AG_AP.electroshop.firebase.SEIConfigCRUD
import com.AG_AP.electroshop.firebase.models.BusinessPartner
import com.AG_AP.electroshop.firebase.models.DocumentLineFireBase
import com.AG_AP.electroshop.firebase.models.Item
import com.AG_AP.electroshop.firebase.models.ItemType
import com.AG_AP.electroshop.firebase.models.OrderFireBase
import com.AG_AP.electroshop.firebase.models.Price
import com.AG_AP.electroshop.firebase.models.SEIConfig
import com.AG_AP.electroshop.functions.Config
import com.AG_AP.electroshop.functions.ConfigurationApplication
import com.AG_AP.electroshop.functions.validarURL
//import com.AG_AP.electroshop.functions.validarURL
import com.AG_AP.electroshop.uiState.SettingUiState
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SettingUiState())
    val uiState: StateFlow<SettingUiState> = _uiState.asStateFlow()

    fun urlExt(data: String){
        _uiState.update { currentState -> currentState.copy(
            urlExt = data
        ) }
    }

    fun urlInt(data: String){
        _uiState.update { currentState -> currentState.copy(
            urlInt = data
        ) }
    }

    fun changeUrlExt(it: String) {
        _uiState.update { currentState -> currentState.copy(
            urlExt = it
        ) }
    }

    fun changeUrlInt(it: String) {
        _uiState.update { currentState -> currentState.copy(
            urlInt = it
        ) }
    }

    fun changeDataBase(it: String) {
        _uiState.update { currentState -> currentState.copy(
            dataBase = it
        ) }
    }

    fun changeUrlUser(it: String) {
        _uiState.update { currentState -> currentState.copy(
            login = it
        ) }
    }

    fun changeUrlPass(it: String) {
        _uiState.update { currentState -> currentState.copy(
            password = it
        ) }
    }

    fun test(){
        var urlInt = _uiState.value.urlInt
        var urlExt = _uiState.value.urlExt
        var login = _uiState.value.login
        var password = _uiState.value.password
        val dataBase = _uiState.value.dataBase
        var textShow = ""

        if(urlInt.isEmpty() || urlExt.isEmpty() || login.isEmpty() || password.isEmpty() || dataBase.isEmpty()){
            textShow = "Todos los campos deben de estar rellenos."
            _uiState.update { currentState -> currentState.copy(
                message = true,
                text = textShow
            ) }
            return;
        }

        if(!validarURL(urlInt) || !validarURL(urlExt)){
            textShow = "Campos de las URL no validos."
            _uiState.update { currentState -> currentState.copy(
                message = true,
                text = textShow
            ) }
            return;
        }

        /*Hacer la conexió*/
        viewModelScope.launch {
            _uiState.update { currentState -> currentState.copy(
                progress = true,
            ) }
            val dataLogin = Login(dataBase,password,login)
            val data = LoginObj.loginAcessTwoversion(dataLogin,urlInt)
            var text:String=""
            Log.e("SettingScreen", "Conexión realizada")
            //LoginObj.logout(urlInt)
            if(data){
                text ="Test realizado con éxito."
                _uiState.update { currentState -> currentState.copy(
                    message = true,
                    text = text,
                    progress = false,
                    ButtomEnable = true
                ) }
                LoginObj.logout(urlInt)
            }else{
                text ="Test NO realizado con éxito."
                _uiState.update { currentState -> currentState.copy(
                    message = true,
                    text = text,
                    progress = false
                ) }
            }
        }
    }

    fun menssageFunFalse(){
        _uiState.update { currentState -> currentState.copy(
            message = false,
            textShow=true,
            syncProgress=false,
            checkUserUdo=false,
            checkBusinessPartner=false,
            checkActivity = false,
            checkItem = false,
            checkOrder = false,
            checkPurchaseOrder = false
        ) }
    }


    private suspend fun creatingUDO(){
        val createFieldEmpleado: CreateField = CreateField("Empleado","Empleado","st_None","@SEICONFIG","db_Numeric",LinkedSystemObject="ulEmployeesInfo")
        val createFieldName: CreateField = CreateField("name","name","st_None","@SEICONFIG","db_Alpha",50)
        val createFielPassword: CreateField = CreateField("password","password","st_None","@SEICONFIG","db_Alpha",50)
        val createFieldItems: CreateField = CreateField("articulo","articulo","st_None","@SEICONFIG","db_Alpha",1)
        val createFieldActivity: CreateField = CreateField("actividad","actividad","st_None","@SEICONFIG","db_Alpha",1)
        val createFieldSalesOrder: CreateField = CreateField("PedidoCl","PedidoCl","st_None","@SEICONFIG","db_Alpha",1)
        val createFieldPruchaseOrde: CreateField = CreateField("PedidoCO","PedidoCO","st_None","@SEICONFIG","db_Alpha",1)

        UDOobj.createField(Config.rulUse,createFieldEmpleado)
        UDOobj.createField(Config.rulUse,createFieldName)
        UDOobj.createField(Config.rulUse,createFielPassword)
        UDOobj.createField(Config.rulUse,createFieldItems)
        UDOobj.createField(Config.rulUse,createFieldActivity)
        UDOobj.createField(Config.rulUse,createFieldSalesOrder)
        UDOobj.createField(Config.rulUse,createFieldPruchaseOrde)

        //ahora creamos el UDO
        //find
        val listFind:List<UserObjectMDFindColumn> = listOf(
            UserObjectMDFindColumn("SEICONFIG","Code","Code",1),
            UserObjectMDFindColumn("SEICONFIG","U_Empleado","Empleado",2),
            UserObjectMDFindColumn("SEICONFIG","U_name","name",3),
            UserObjectMDFindColumn("SEICONFIG","U_password","password",4),
            UserObjectMDFindColumn("SEICONFIG","U_articulo","articulo",5),
            UserObjectMDFindColumn("SEICONFIG","U_actividad","actividad",6),
            UserObjectMDFindColumn("SEICONFIG","U_PedidoCl","PedidoCl",7),
            UserObjectMDFindColumn("SEICONFIG","U_PedidoCO","PedidoCO",8)
        )
        val listForm:List<UserObjectMDFormColumn> = listOf(
            UserObjectMDFormColumn("SEICONFIG","tYES","Code","Code",1,0),
            UserObjectMDFormColumn("SEICONFIG","tYES","U_Empleado","Empleado",2,0),
            UserObjectMDFormColumn("SEICONFIG","tYES","U_name","name",3,0),
            UserObjectMDFormColumn("SEICONFIG","tYES","U_password","password",4,0),
            UserObjectMDFormColumn("SEICONFIG","tYES","U_articulo","articulo",5,0),
            UserObjectMDFormColumn("SEICONFIG","tYES","U_actividad","actividad",6,0),
            UserObjectMDFormColumn("SEICONFIG","tYES","U_PedidoCl","PedidoCl",7,0),
            UserObjectMDFormColumn("SEICONFIG","tYES","U_PedidoCO","PedidoCO",8,0)
        )
        UDOobj.createUDO(Config.rulUse, CreateUdo("SEICONFIG","SEICONFIG","SEICONFIG","tYES","tYES","tYES","tYES","tYES",listFind,listForm))
        Log.e("UDO","UDO CREADO")
    }

    private suspend fun createUsers(){
        val user1: CreateUserUDO = CreateUserUDO("1",3,"N","N","N","N","JFR","usuario")
        val user2: CreateUserUDO = CreateUserUDO("2",2,"N","N","N","N","ARM","usuario")
        val user3: CreateUserUDO = CreateUserUDO("3",4,"N","N","N","N","SCE","usuario")
        val user4: CreateUserUDO = CreateUserUDO("4",5,"S","S","S","S","manager","usuario")
        UDOobj.createuserSEICONFIG(Config.rulUse,user1)
        UDOobj.createuserSEICONFIG(Config.rulUse,user2)
        UDOobj.createuserSEICONFIG(Config.rulUse,user3)
        UDOobj.createuserSEICONFIG(Config.rulUse,user4)
        Log.e("UDO","Usuarios añadidos")
    }

    private suspend fun UserUdoInsertFireBase(userForUdo: SeiConfigUser?) {
        if(userForUdo is SeiConfigUser){
            userForUdo.value.forEach { element->
            SEIConfigCRUD.insertSEIConfig(
                SEIConfig(
                element.Code.toInt(),
                element.U_Empleado,
                element.U_name,
                element.U_password,
                element.U_articulo,
                element.U_actividad,
                element.U_PedidoCl,
                element.U_PedidoCO
            )
            )
                Log.e("JOSELITOLOQUITO","Usuarios check")
        }
            _uiState.update { currentState -> currentState.copy(
                checkUserUdo = true
            ) }
        }
    }

    private fun deleteAndInsertUserUdo() {
        viewModelScope.launch(Dispatchers.IO) {
            val userForUdo = UDOobj.getUserTableUDO(Config.rulUse)
            if(userForUdo is SeiConfigUser ){
                userForUdo.value.forEach { element->
                    Log.e("JOSELITOO",element.U_name)
                    SEIConfigCRUD.deleteSEIConfigById(element.U_name)
                }
                println("aaaaaaaaaaaaaaaaaaa")
            }

            UserUdoInsertFireBase(userForUdo)
        }
    }

    private fun deleteAndInsertBusinessPartner() {
        viewModelScope.launch(Dispatchers.IO) {
            var listBusinessPartnerSAP: MutableList<com.AG_AP.electroshop.endpoints.models.businessPartners.Value> =
                mutableListOf()
            var checkSAP: Boolean = false
            var BusinessPartners = BusinessPartnersObj.getBusinessPartners(Config.rulUse)
            while (!checkSAP) {
                if (BusinessPartners != null) {
                    if (BusinessPartners.odataNextLink.isNullOrEmpty()) {
                        BusinessPartners.value.forEach {
                                listBusinessPartnerSAP += it
                        }
                        checkSAP=true
                    }else{
                        BusinessPartners.value.forEach {
                                listBusinessPartnerSAP += it
                        }
                        val num :List<String> = BusinessPartners.odataNextLink.split("=")
                        BusinessPartners =BusinessPartnersObj.getBusinessPartnersExten(Config.rulUse,num[1].toInt())
                    }
                }
            }

            listBusinessPartnerSAP.forEach { element->
                    BusinessPartnerCRUD.deleteObjectById(element.CardCode)
                }

            listBusinessPartnerSAP.forEach { element ->
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
                _uiState.update { currentState -> currentState.copy(
                    checkBusinessPartner = true
                ) }

            Log.e("sync","clientes sincronizados")
        }
    }

    private fun deleteAndInsertActivity() {
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

            //Aqui ya debe de entrar con todas las de SAP
            listActivitySAP.forEach { element ->
                ActivityCRUD.deleteActivityById(element.ActivityCode.toString())
            }
            listActivitySAP.forEach { element ->
                val activity: com.AG_AP.electroshop.firebase.models.Activity =
                    com.AG_AP.electroshop.firebase.models.Activity(
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
            _uiState.update { currentState ->
                currentState.copy(
                    checkActivity = true
                )
            }

            Log.e("sync", "actividades sincronizados")
        }
    }

    fun sync(context: Context) {

        viewModelScope.launch() {
                _uiState.update { currentState -> currentState.copy(
                    message = true,
                    text = "Sincronización realizada",
                    progress = false,
                    syncProgress=true,
                    textShow=false,
                    btnSyncEnable = false,
                    btnEnable=false,
                    btnExitEnable=false
                ) }
                val dataLogin = Login(Config.dataBase,Config.password,Config.login)
                LoginObj.loginAcessTwoversion(dataLogin,Config.rulUse)
            //TODO
                deleteAndInsertPriceList()
                deleteAndInsertItem()// Correcta
                deleteAndInsertUserUdo() //
                deleteAndInsertBusinessPartner() // Correcta
                deleteAndInsertActivity() // Correcto
                deleteAndInsertOrders() //Corecta
                deleteAndInsertPurchaseOrders() //Correcta
                enablebtn(Config.rulUse)
        }


        /*
         Log.e("SettingViewModel","Datos obtenidos")

         //UDO
         val res : Boolean = UDOobj.createTable(Config.rulUse)
         if(res){
             viewModelScope.launch {
                 creatingUDO()
                 createUsers()
             }
             text ="Conexión realizada"
             _uiState.update { currentState -> currentState.copy(
                 message = true,
                 text = text,
                 progress = false
             ) }
         }*/
    }

    private fun enablebtn(url:String) {
        viewModelScope.launch(Dispatchers.IO) {
            var aux: Boolean = true
            while (aux){
                if(_uiState.value.checkUserUdo && _uiState.value.checkBusinessPartner && _uiState.value.checkActivity && _uiState.value.checkItem && _uiState.value.checkOrder && _uiState.value.checkPurchaseOrder){
                    aux = false
                    LoginObj.logout(url)
                } else {
                    delay(1000)
                }
            }
            _uiState.update { currentState -> currentState.copy(
                btnSyncEnable = true,
                btnEnable=true,
                btnExitEnable=true
            ) }
        }
    }

    private fun deleteAndInsertPurchaseOrders() {
        viewModelScope.launch(Dispatchers.IO) {
            var listPurchaseOrderSAP: MutableList<com.AG_AP.electroshop.endpoints.models.purchaseOrders.Value> =
                mutableListOf()
            var checkSAP: Boolean = false
            var purchaseOrders : PurchaseOrders? = PurchaseOrdersObj.getPurchaseOrders(Config.rulUse)
            while (!checkSAP) {
                if (purchaseOrders != null) {
                    if (purchaseOrders.odataNextLink.isNullOrEmpty()) {
                        purchaseOrders.value.forEach {
                            listPurchaseOrderSAP += it
                        }
                        checkSAP=true
                    }else{
                        purchaseOrders.value.forEach {
                            listPurchaseOrderSAP += it
                        }
                        val num :List<String> = purchaseOrders.odataNextLink.split("=")
                        purchaseOrders =PurchaseOrdersObj.getPurchaseOrdersExtenExten(Config.rulUse,num[1].toInt())
                    }
                }
            }
            listPurchaseOrderSAP.forEach{element ->
                PurchaseOrderCRUD.deleteObjectById(element.DocNum.toString())
            }
            listPurchaseOrderSAP.forEach{element ->
                //lista de precios
                val documentList: MutableList<DocumentLineFireBase> = mutableListOf()
                element.DocumentLines.forEachIndexed { index, it ->
                    documentList.add(
                        index,
                        DocumentLineFireBase(
                            it.ItemCode,
                            it.ItemDescription ?: "",
                            it.Quantity,
                            it.DiscountPercent,
                            it.LineNum,
                            it.Price,
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
                PurchaseOrderCRUD.insert(orderInsert)
            }
            _uiState.update { currentState -> currentState.copy(
                checkPurchaseOrder = true
            ) }
            Log.e("sync","order sync")
        }
    }

    private fun deleteAndInsertOrders() {
        viewModelScope.launch(Dispatchers.IO) {
            var listOrderSAP: MutableList<com.AG_AP.electroshop.endpoints.models.orders.Value> =
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
                                it.ItemDescription ?: "",
                                it.Quantity,
                                it.DiscountPercent,
                                it.LineNum,
                                it.Price,
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
                }
                _uiState.update { currentState -> currentState.copy(
                    checkOrder = true
                ) }
            Log.e("sync","order sync")
        }
    }

    private fun deleteAndInsertItem() {
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
                        listPrice.add(
                            index,
                            Price(
                                itemPrice.PriceList ?:0,
                                itemPrice.Price ?:0.0F,
                                itemPrice.Currency ?:"",
                                true
                            )
                        )
                    }
                    val item : Item = Item(
                        "",
                        element.ItemCode ?: "",
                        element.ItemName ?: "",
                        ItemType.Articulo,
                        element.ItemName ?: "",
                        listPrice.toList(),
                                element.ManageSerialNumbers ?: "",
                                element.AutoCreateSerialNumbersOnRelease ?: "",
                        true
                    )
                    ItemCRUD.insertItem(item)
                }
                _uiState.update { currentState -> currentState.copy(
                    checkItem = true
                ) }

            Log.e("sync","actividades sincronizados")
        }
    }

    fun saveConfiguration(context: Context) {
        val urlInt = _uiState.value.urlInt
        val urlExt = _uiState.value.urlExt
        val login = _uiState.value.login
        val password = _uiState.value.password
        val dataBase = _uiState.value.dataBase
        val dataConfiguration = ConfigurationApplication(login,password,dataBase,urlExt,urlInt)
        val gson = Gson()
        val jsonData:String = gson.toJson(dataConfiguration)

        val sharedPref = context?.getSharedPreferences("configuration", Context.MODE_PRIVATE)
        val dataConfig = sharedPref?.edit()
        dataConfig?.putString("configuration", jsonData)
        dataConfig?.apply()
        Config.initConfig(context)

        //val jsonStringRecuperada = sharedPref?.getString("configuration", null)
        //val miObjetoRecuperado = gson.fromJson(jsonStringRecuperada, ConfigurationApplication::class.java)
        _uiState.update { currentState -> currentState.copy(
            message = true,
            text = "Configuración guardada con éxito.",
            progress = false
        ) }

    }

    fun initData(context: Context){
        val gson = Gson()
        val sharedPref = context?.getSharedPreferences("configuration", Context.MODE_PRIVATE)
        val json = sharedPref?.getString("configuration", null)
        if (!json.isNullOrEmpty()) {
            val dataConfig = gson.fromJson(json, ConfigurationApplication::class.java)
            _uiState.update { currentState -> currentState.copy(
                urlInt = dataConfig.urlInt,
                urlExt = dataConfig.urlExt,
                login = dataConfig.login,
                password = dataConfig.password,
                dataBase = dataConfig.dataBase,
                init=false
            ) }
        }
    }

    private fun deleteAndInsertPriceList() {
        viewModelScope.launch(Dispatchers.IO) {
            val priceList = PriceListObj.getPriceLists(Config.rulUse)
            if(priceList is PriceList){
                priceList.value.forEach { element->
                    Log.e("JOSELITOO",element.toString())
                    PriceListCRUD.deletePrecioById(element.BasePriceList.toString())
                }
                priceList.value.forEach { element->
                    PriceListCRUD.insertPrecio(
                        element.BasePriceList,
                        element.FixedAmount.toDouble(),
                        element.DefaultPrimeCurrency,
                        true
                    )
                }
            }
        }
    }
}