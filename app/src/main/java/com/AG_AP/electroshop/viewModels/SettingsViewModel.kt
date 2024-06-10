package com.AG_AP.electroshop.viewModels

import android.content.Context
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.AG_AP.electroshop.endpoints.models.activity.Activity
import com.AG_AP.electroshop.endpoints.models.exportToPDF.DataPostExportToPDF
import com.AG_AP.electroshop.endpoints.models.exportToPDF.DataPostExportToPDFItem
import com.AG_AP.electroshop.endpoints.models.invoices.InvoicesGet
import com.AG_AP.electroshop.endpoints.models.item.getItems.GetItems
import com.AG_AP.electroshop.endpoints.models.login.Login
import com.AG_AP.electroshop.endpoints.models.orders.Orders
import com.AG_AP.electroshop.endpoints.models.priceList.PriceList
import com.AG_AP.electroshop.endpoints.objects.ActivityObj
import com.AG_AP.electroshop.endpoints.objects.BusinessPartnersObj
import com.AG_AP.electroshop.endpoints.objects.ExportToPDFObj
import com.AG_AP.electroshop.endpoints.objects.ItemObj
import com.AG_AP.electroshop.endpoints.objects.LoginObj
import com.AG_AP.electroshop.endpoints.objects.OrdersObj
import com.AG_AP.electroshop.endpoints.objects.PriceListObj
import com.AG_AP.electroshop.endpoints.udo.models.CreateField
import com.AG_AP.electroshop.endpoints.udo.models.createUdo.CreateUdo
import com.AG_AP.electroshop.endpoints.udo.models.createUdo.UserObjectMDFindColumn
import com.AG_AP.electroshop.endpoints.udo.models.createUdo.UserObjectMDFormColumn
import com.AG_AP.electroshop.endpoints.udo.models.createUserUDO.CreateUserUDO
import com.AG_AP.electroshop.endpoints.udo.models.getUserUdo.SeiConfigUser
import com.AG_AP.electroshop.endpoints.udo.objects.UDOobj
import com.AG_AP.electroshop.realm.ActivityCRUD
import com.AG_AP.electroshop.realm.BusinessPartnerCRUD
import com.AG_AP.electroshop.realm.InvoiceDataCRUD
import com.AG_AP.electroshop.realm.ItemCRUD
import com.AG_AP.electroshop.realm.OrderCRUD
import com.AG_AP.electroshop.realm.PriceListForListCRUD
import com.AG_AP.electroshop.realm.SEIConfigCRUD
import com.AG_AP.electroshop.realm.SpecialPricesCRUD
import com.AG_AP.electroshop.realm.models.BusinessPartner
import com.AG_AP.electroshop.realm.models.DocumentLineRealm
import com.AG_AP.electroshop.realm.models.InvoiceData
import com.AG_AP.electroshop.realm.models.Item
import com.AG_AP.electroshop.realm.models.OrderRealm
import com.AG_AP.electroshop.realm.models.ItemPrice
import com.AG_AP.electroshop.realm.models.PriceListRealm
import com.AG_AP.electroshop.realm.models.SEIConfig
import com.AG_AP.electroshop.realm.models.SpecialPriceRealm
import com.AG_AP.electroshop.functions.Config
import com.AG_AP.electroshop.functions.ConfigurationApplication
import com.AG_AP.electroshop.functions.validarURL
//import com.AG_AP.electroshop.functions.validarURL
import com.AG_AP.electroshop.uiState.SettingUiState
import com.google.gson.Gson
import io.realm.kotlin.ext.toRealmList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response

class SettingsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SettingUiState())
    val uiState: StateFlow<SettingUiState> = _uiState.asStateFlow()

    fun urlExt(data: String) {
        _uiState.update { currentState ->
            currentState.copy(
                urlExt = data
            )
        }
    }

    fun urlInt(data: String) {
        _uiState.update { currentState ->
            currentState.copy(
                urlInt = data
            )
        }
    }

    fun changeUrlExt(it: String) {
        _uiState.update { currentState ->
            currentState.copy(
                urlExt = it
            )
        }
    }

    fun changeUrlInt(it: String) {
        _uiState.update { currentState ->
            currentState.copy(
                urlInt = it
            )
        }
    }

    fun changeDataBase(it: String) {
        _uiState.update { currentState ->
            currentState.copy(
                dataBase = it
            )
        }
    }

    fun changeUrlUser(it: String) {
        _uiState.update { currentState ->
            currentState.copy(
                login = it
            )
        }
    }

    fun changeUrlPass(it: String) {
        _uiState.update { currentState ->
            currentState.copy(
                password = it
            )
        }
    }

    fun test() {
        var urlInt = _uiState.value.urlInt
        var urlExt = _uiState.value.urlExt
        var login = _uiState.value.login
        var password = _uiState.value.password
        val dataBase = _uiState.value.dataBase
        var textShow = ""

        if (urlInt.isEmpty() || urlExt.isEmpty() || login.isEmpty() || password.isEmpty() || dataBase.isEmpty()) {
            textShow = "Todos los campos deben de estar rellenos."
            _uiState.update { currentState ->
                currentState.copy(
                    message = true,
                    text = textShow
                )
            }
            return;
        }

        if (!validarURL(urlInt) || !validarURL(urlExt)) {
            textShow = "Campos de las URL no validos."
            _uiState.update { currentState ->
                currentState.copy(
                    message = true,
                    text = textShow
                )
            }
            return;
        }

        /*Hacer la conexió*/
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    progress = true,
                )
            }
            val dataLogin = Login(dataBase, password, login)
            val data = LoginObj.loginAcessTwoversion(dataLogin, urlInt)
            var dataUrlExt: Boolean = false
            var urlCheck: String = urlInt
            var urlCheckTip: String = "Int"
            if (!data) {
                dataUrlExt = LoginObj.loginAcessTwoversion(dataLogin, urlExt)
                urlCheck = urlExt
                urlCheckTip = "Ext"
            }
            var text: String = ""
            Log.e("SettingScreen", "Conexión realizada")
            //LoginObj.logout(urlInt)
            if (data || dataUrlExt) {
                text = "Test realizado con éxito."
                if (data) {
                    _uiState.update { currentState ->
                        currentState.copy(
                            message = true,
                            text = text,
                            progress = false,
                            ButtomEnable = true,
                            urlCheck = urlCheck,
                            urlTipCheck = urlCheckTip,
                            iconInt = Icons.Default.CheckCircle,
                            iconExt = Icons.Default.Cancel
                        )
                    }
                    LoginObj.logout(urlInt)
                } else if (dataUrlExt) {
                    _uiState.update { currentState ->
                        currentState.copy(
                            message = true,
                            text = text,
                            progress = false,
                            ButtomEnable = true,
                            urlCheck = urlCheck,
                            urlTipCheck = urlCheckTip,
                            iconExt = Icons.Default.CheckCircle,
                            iconInt = Icons.Default.Cancel
                        )
                    }
                    LoginObj.logout(urlExt)
                }
            } else {
                text = "Test NO realizado con éxito."
                _uiState.update { currentState ->
                    currentState.copy(
                        message = true,
                        text = text,
                        progress = false
                    )
                }
            }
        }
    }

    fun menssageFunFalse() {
        _uiState.update { currentState ->
            currentState.copy(
                message = false,
                textShow = true,
                syncProgress = false,
                checkUserUdo = false,
                checkBusinessPartner = false,
                checkActivity = false,
                checkItem = false,
                checkOrder = false,
                checkPurchaseOrder = false
            )
        }
    }


    private suspend fun creatingUDO() {
        val createFieldEmpleado: CreateField = CreateField(
            "SEI_empleado",
            "SEI_empleado",
            "st_None",
            "@SEICONFIG",
            "db_Numeric",
            LinkedSystemObject = "ulEmployeesInfo"
        )
        val createFieldName: CreateField =
            CreateField("SEI_name", "SEI_name", "st_None", "@SEICONFIG", "db_Alpha", 50)
        val createFielPassword: CreateField =
            CreateField("SEI_password", "SEI_password", "st_None", "@SEICONFIG", "db_Alpha", 50)
        val createFieldItems: CreateField =
            CreateField("SEI_articulo", "SEI_articulo", "st_None", "@SEICONFIG", "db_Alpha", 1)
        val createFieldActivity: CreateField =
            CreateField("SEI_actividad", "SEI_actividad", "st_None", "@SEICONFIG", "db_Alpha", 1)
        val createFieldSalesOrder: CreateField =
            CreateField("SEI_pedidoCl", "SEI_pedidoCl", "st_None", "@SEICONFIG", "db_Alpha", 1)
        val createFieldPruchaseOrde: CreateField =
            CreateField("SEI_pedidoCO", "SEI_pedidoCO", "st_None", "@SEICONFIG", "db_Alpha", 1)

        UDOobj.createField(Config.rulUse, createFieldEmpleado)
        UDOobj.createField(Config.rulUse, createFieldName)
        UDOobj.createField(Config.rulUse, createFielPassword)
        UDOobj.createField(Config.rulUse, createFieldItems)
        UDOobj.createField(Config.rulUse, createFieldActivity)
        UDOobj.createField(Config.rulUse, createFieldSalesOrder)
        UDOobj.createField(Config.rulUse, createFieldPruchaseOrde)

        //ahora creamos el UDO
        //find
        val listFind: List<UserObjectMDFindColumn> = listOf(
            UserObjectMDFindColumn("SEICONFIG", "Code", "Code", 1),
            UserObjectMDFindColumn("SEICONFIG", "U_SEI_empleado", "SEI_empleado", 2),
            UserObjectMDFindColumn("SEICONFIG", "U_SEI_name", "SEI_name", 3),
            UserObjectMDFindColumn("SEICONFIG", "U_SEI_password", "SEI_password", 4),
            UserObjectMDFindColumn("SEICONFIG", "U_SEI_articulo", "SEI_articulo", 5),
            UserObjectMDFindColumn("SEICONFIG", "U_SEI_actividad", "SEI_actividad", 6),
            UserObjectMDFindColumn("SEICONFIG", "U_SEI_pedidoCl", "SEI_pedidoCl", 7),
            UserObjectMDFindColumn("SEICONFIG", "U_SEI_pedidoCO", "SEI_pedidoCO", 8)
        )
        val listForm: List<UserObjectMDFormColumn> = listOf(
            UserObjectMDFormColumn("SEICONFIG", "tYES", "Code", "Code", 1, 0),
            UserObjectMDFormColumn("SEICONFIG", "tYES", "U_SEI_empleado", "SEI_empleado", 2, 0),
            UserObjectMDFormColumn("SEICONFIG", "tYES", "U_SEI_name", "SEI_name", 3, 0),
            UserObjectMDFormColumn("SEICONFIG", "tYES", "U_SEI_password", "SEI_password", 4, 0),
            UserObjectMDFormColumn("SEICONFIG", "tYES", "U_SEI_articulo", "SEI_articulo", 5, 0),
            UserObjectMDFormColumn("SEICONFIG", "tYES", "U_SEI_actividad", "SEI_actividad", 6, 0),
            UserObjectMDFormColumn("SEICONFIG", "tYES", "U_SEI_pedidoCl", "SEI_pedidoCl", 7, 0),
            UserObjectMDFormColumn("SEICONFIG", "tYES", "U_SEI_pedidoCO", "SEI_pedidoCO", 8, 0)
        )
        UDOobj.createUDO(
            Config.rulUse,
            CreateUdo(
                "SEICONFIG",
                "SEICONFIG",
                "SEICONFIG",
                "tYES",
                "tYES",
                "tYES",
                "tYES",
                "tYES",
                listFind,
                listForm,
                "tNO",
                "tYES",
                "SEICONFIG",
                2048,
                12,
                "SEICONFIG"
            )
        )
        Log.e("UDO", "UDO CREADO")
    }

    private suspend fun createUsers() {
        val user4: CreateUserUDO = CreateUserUDO("1", 5, "S", "S", "S", "S", "manager", "usuario")

        UDOobj.createuserSEICONFIG(Config.rulUse, user4)
        Log.e("UDO", "Usuarios añadidos")
    }

    private suspend fun UserUdoInsertFireBase(userForUdo: SeiConfigUser?) {
        if (userForUdo is SeiConfigUser) {
            userForUdo.value.forEach { element ->
                val seiConfig = SEIConfig().apply {
                    this.Code = element.Code.toInt()
                    this.U_Empleado = element.U_SEI_Empleado ?: 0
                    this.U_name = element.U_SEI_name ?: ""
                    this.U_password = element.U_SEI_password ?: ""
                    this.U_articulo = element.U_SEI_articulo ?: ""
                    this.U_actividad = element.U_SEI_actividad ?: ""
                    this.U_PedidoCI = element.U_SEI_PedidoCl ?: ""
                    this.U_PedidoCO = element.U_SEI_PedidoCO ?: ""
                }
                SEIConfigCRUD.insertSEIConfig(
                    seiConfig
                )
                Log.e("JOSELITOLOQUITO", "Usuarios check")
            }
            _uiState.update { currentState ->
                currentState.copy(
                    checkUserUdo = true
                )
            }
        }
    }

    private fun deleteAndInsertUserUdo() {
        viewModelScope.launch(Dispatchers.IO) {
            val res: Boolean = UDOobj.createTable(Config.rulUse)
            if (res) {
                creatingUDO()
                createUsers()
            }
            val userForUdo = UDOobj.getUserTableUDO(Config.rulUse)
            if (userForUdo is SeiConfigUser) {

                println("aaaaaaaaaaaaaaaaaaa")
            }
            //Borramos todos
            SEIConfigCRUD.deleteAll()

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
                        checkSAP = true
                    } else {
                        BusinessPartners.value.forEach {
                            listBusinessPartnerSAP += it
                        }
                        val num: List<String> = BusinessPartners.odataNextLink.split("=")
                        BusinessPartners = BusinessPartnersObj.getBusinessPartnersExten(
                            Config.rulUse,
                            num[1].toInt()
                        )
                    }
                }
            }

            //Borramos todos los interlocutores
            BusinessPartnerCRUD.deleteAll()

            listBusinessPartnerSAP.forEach { element ->
                var email: String = ""
                var phone1: String = ""
                var CardName: String = ""
                if (!element.Phone1.isNullOrEmpty()) {
                    phone1 = element.Phone1
                }
                if (!element.EmailAddress.isNullOrEmpty()) {
                    email = element.EmailAddress
                }
                if (!element.CardName.isNullOrEmpty()) {
                    CardName = element.CardName
                }

                val bp = BusinessPartner().apply {
                    this.idFireBase = ""
                    this.CardCode = element.CardCode
                    this.CardType = element.CardType
                    this.CardName = CardName
                    this.Cellular = phone1
                    this.EmailAddress = email
                    this.SAP = true
                }

                BusinessPartnerCRUD.insert(
                    bp
                )
            }
            _uiState.update { currentState ->
                currentState.copy(
                    checkBusinessPartner = true
                )
            }

            Log.e("sync", "clientes sincronizados")
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
                        checkSAP = true
                    } else {
                        activities.value.forEach {
                            listActivitySAP += it
                        }
                        val num: List<String> = activities.odataNextLink.split("=")
                        activities = ActivityObj.getActivitiesExten(Config.rulUse, num[1].toInt())
                    }
                }
            }

            //Aqui ya debe de entrar con todas las de SAP
            listActivitySAP.forEach { element ->
                ActivityCRUD.deleteActivityById(element.ActivityCode.toString())
            }
            listActivitySAP.forEach { element ->
                val activity: com.AG_AP.electroshop.realm.models.Activity =
                    com.AG_AP.electroshop.realm.models.Activity().apply {
                        this.idFireBase = ""
                        this.nota = element.Notes ?: ""
                        this.ActivityDate = element.ActivityDate ?: ""
                        this.ActivityTime = element.ActivityTime ?: ""
                        this.CardCode = element.CardCode ?: ""
                        this.EndTime = element.EndTime ?: ""
                        this.Action = element.Activity ?: ""
                        this.ClgCode = element.ActivityCode.toString() ?: ""
                        this.Priority = element.Priority ?: ""
                        this.U_SEIPEDIDOCOMPRAS = element.U_SEIPEDIDOCOMPRAS ?: 0
                        this.U_SEIPEDIDOCLIENTE = element.U_SEIPEDIDOCLIENTE ?: 0
                        this.SAP = true
                    }
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
        _uiState.value.checkInvoices = false
        viewModelScope.launch() {
            _uiState.update { currentState ->
                currentState.copy(
                    message = true,
                    text = "Sincronización realizada",
                    progress = false,
                    syncProgress = true,
                    textShow = false,
                    btnSyncEnable = false,
                    btnEnable = false,
                    btnExitEnable = false
                )
            }
            val dataLogin = Login(Config.dataBase, Config.password, Config.login)
            LoginObj.loginAcessTwoversion(dataLogin, Config.rulUse)
            //TODO
            deleteAndInsertSpecialPrice() // correcta
            deleteAndInsertPriceList() // correcta
            deleteAndInsertInvoice()
            if(_uiState.value.checkBoxItems){
                deleteAndInsertItem()// Correcta
            }else{
                _uiState.update { currentState ->
                    currentState.copy(
                        checkItem = true
                    )
                }
            }

            if(_uiState.value.checkBoxUDO){
                deleteAndInsertUserUdo() // revisado
            }else{
                _uiState.update { currentState ->
                    currentState.copy(
                        checkUserUdo = true
                    )
                }
            }

            if(_uiState.value.checkBoxClients){
                deleteAndInsertBusinessPartner() // revisado
            }else{
                _uiState.update { currentState ->
                    currentState.copy(
                        checkBusinessPartner = true
                    )
                }
            }
            if(_uiState.value.checkBoxActivity){
                deleteAndInsertActivity() // revisado
            }else{
                _uiState.update { currentState ->
                    currentState.copy(
                        checkActivity = true
                    )
                }
            }
            if(_uiState.value.checkBoxOrders){
                deleteAndInsertOrders() //
            }else{
                _uiState.update { currentState ->
                    currentState.copy(
                        checkOrder = true
                    )
                }
            }
            enablebtn(Config.rulUse)
        }
        Log.e("SettingViewModel", "Datos obtenidos")

    }

    private fun deleteAndInsertInvoice() {
        //TODO
        viewModelScope.launch(Dispatchers.IO) {
            val data: InvoicesGet? = OrdersObj.getInvoices(Config.rulUse)
            if (data != null) {
                val dataLogin = Login(Config.dataBase, Config.password, Config.login)
                LoginObj.loginAcessGateway(dataLogin, Config.rulUse)

                InvoiceDataCRUD.deleteAll()

                data.value.forEach { it ->
                    var cardCode = it.CardCode
                    var DocNum= it.DocNum
                    var docEntry = it.DocEntry.toString()
                    var data: DataPostExportToPDF = DataPostExportToPDF()
                    data.add(
                        DataPostExportToPDFItem(
                            "DocKey@",
                            "xsd:string",
                            listOf(listOf(docEntry))
                        )
                    )
                    data.add(
                        DataPostExportToPDFItem(
                            "ObjectId@",
                            "xsd:decimal",
                            listOf(listOf("13"))
                        )
                    )
                    var response: Response<ResponseBody>? = ExportToPDFObj.postExporToPDF(data)
                    if (response != null && response.isSuccessful) {
                        val responseBody: ResponseBody? = response.body()
                        if (responseBody != null) {
                            val responseText = responseBody.string()
                            Log.i("ExportToPDF", responseText)
                            //TODO REALM


                            val Invoice = InvoiceData().apply {
                                this.CardCode = cardCode
                                this.DocNum = DocNum
                                this.DocEntry = docEntry.toLong()
                                this.Base64String = responseText
                            }

                            InvoiceDataCRUD.insert(Invoice)
                        }
                    }
                }
                //Fin de la conexión
                LoginObj.logoutGateway(Config.rulUse)
                _uiState.value.checkInvoices = true
            }else{
                _uiState.value.checkInvoices = true
            }

        }
    }

    private fun deleteAndInsertSpecialPrice() {
        viewModelScope.launch(Dispatchers.IO) {
            var listSpecialPricesSAP: MutableList<com.AG_AP.electroshop.endpoints.models.specialPrices.Value> =
                mutableListOf()
            var checkSAP: Boolean = false
            var specialPrices = ItemObj.getSpecialPrices(Config.rulUse)
            while (!checkSAP) {
                if (specialPrices != null) {
                    if (specialPrices.odataNextLink.isNullOrEmpty()) {
                        specialPrices.value.forEach {
                            listSpecialPricesSAP += it
                        }
                        checkSAP = true
                    } else {
                        specialPrices.value.forEach {
                            listSpecialPricesSAP += it
                        }
                        val num: List<String> = specialPrices.odataNextLink.split("=")
                        //TODO("Revisar si se quiere añadir mas descuentos especiales")
                        /*specialPrices = BusinessPartnersObj.getBusinessPartnersExten(
                            Config.rulUse,
                            num[1].toInt()
                        )*/
                    }
                }
            }

            //Borramos todos los precios especiales
            SpecialPricesCRUD.deleteAll()

            listSpecialPricesSAP.forEach { element ->

                val bp = SpecialPriceRealm().apply {
                    this.Price = element.Price ?: 0.0
                    this.CardCode = element.CardCode ?: ""
                    this.ItemCode = element.ItemCode ?: ""
                    this.Currency = element.Currency ?: ""
                    this.PriceListNum = element.PriceListNum ?: 0
                    this.DiscountPercent = element.DiscountPercent ?: 0.0
                }
                SpecialPricesCRUD.insert(
                    bp
                )
            }
            _uiState.update { currentState ->
                currentState.copy(
                    checkPreciosEspeciales = true
                )
            }

            Log.e("sync", "Precios especiales sincronizados")
        }
    }

    private fun enablebtn(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            var aux: Boolean = true
            while (aux) {
                if (_uiState.value.checkUserUdo && _uiState.value.checkBusinessPartner && _uiState.value.checkActivity && _uiState.value.checkItem && _uiState.value.checkPreciosEspeciales && _uiState.value.checkPriceLists && _uiState.value.checkInvoices) {

                    aux = false
                    LoginObj.logout(url)
                } else {
                    delay(1000)
                }
            }
            _uiState.update { currentState ->
                currentState.copy(
                    btnSyncEnable = true,
                    btnEnable = true,
                    btnExitEnable = true
                )
            }
        }
    }

    private fun deleteAndInsertOrders() {
        viewModelScope.launch(Dispatchers.IO) {
            var listOrderSAP: MutableList<com.AG_AP.electroshop.endpoints.models.orders.Value> =
                mutableListOf()
            var checkSAP: Boolean = false
            var orders: Orders? = OrdersObj.getOrders(Config.rulUse)
            while (!checkSAP) {
                if (orders != null) {
                    if (orders.odataNextLink.isNullOrEmpty()) {
                        orders.value.forEach {
                            listOrderSAP += it
                        }
                        checkSAP = true
                    } else {
                        orders.value.forEach {
                            listOrderSAP += it
                        }
                        val num: List<String> = orders.odataNextLink.split("=")
                        orders = OrdersObj.getOrdersExtenExten(Config.rulUse, num[2].toInt())
                    }
                }
            }

            //Borramos todos los pedidos
            OrderCRUD.deleteAll()

            listOrderSAP.forEach { element ->
                //lista de precios
                val documentList: MutableList<DocumentLineRealm> = mutableListOf()
                element.DocumentLines.forEachIndexed { index, it ->
                    val line = DocumentLineRealm().apply {
                        ItemCode = it.ItemCode
                        ItemDescription = it.ItemDescription ?: ""
                        Quantity = it.Quantity
                        DiscountPercent = it.DiscountPercent
                        LineNum = it.LineNum
                        Price = it.Price
                    }
                    documentList.add(
                        index,
                        line
                    )
                }
                val orderInsert: OrderRealm = OrderRealm().apply {
                    this.idFireBase = ""
                    DocNum = element.DocNum
                    CardCode = element.CardCode
                    CardName = element.CardName
                    DocDate = element.DocDate
                    DocDueDate = element.DocDueDate
                    TaxDate = element.TaxDate
                    DiscountPercent = element.DiscountPercent
                    this.DocumentLines = documentList.toRealmList()
                    this.SAP = true
                    SalesPersonCode = element.SalesPersonCode
                }
                OrderCRUD.insert(orderInsert)
            }
            _uiState.update { currentState ->
                currentState.copy(
                    checkOrder = true
                )
            }
            Log.e("sync", "order sync")
        }
    }

    private fun deleteAndInsertItem() {
        viewModelScope.launch(Dispatchers.IO) {
            var listItemSAP: MutableList<com.AG_AP.electroshop.endpoints.models.item.getItems.Value> =
                mutableListOf()
            var checkSAP: Boolean = false
            var items: GetItems? = ItemObj.getItems(Config.rulUse)
            while (!checkSAP) {
                if (items != null) {
                    if (items.odataNextLink.isNullOrEmpty()) {
                        items.value.forEach {
                            listItemSAP += it
                        }
                        checkSAP = true
                    } else {
                        items.value.forEach {
                            listItemSAP += it
                        }
                        val num: List<String> = items.odataNextLink.split("=")
                        items = ItemObj.getItemsExten(Config.rulUse, num[1].toInt())
                    }
                }
            }

            //Borramos los items
            ItemCRUD.deleteAll()

            listItemSAP.forEach { element ->
                //lista de precios
                val listPrice: MutableList<ItemPrice> = mutableListOf()
                element.ItemPrices.forEachIndexed { index, itemPrice ->
                    val price = ItemPrice().apply {
                        this.priceList = itemPrice.PriceList ?: 0
                        price = itemPrice.Price ?: 0.0
                        currency = itemPrice.Currency ?: ""
                        SAP = true
                    }
                    listPrice.add(
                        index,
                        price
                    )
                }
                val item: Item = Item().apply {
                    idFireBase = ""
                    ItemCode = element.ItemCode ?: ""
                    itemName = element.ItemName ?: ""
                    itemType = "I"
                    mainSupplier = element.ItemName ?: ""
                    this.itemPrice = listPrice.toRealmList()
                    manageSerialNumbers = element.ManageSerialNumbers ?: ""
                    autoCreateSerialNumbersOnRelease =
                        element.AutoCreateSerialNumbersOnRelease ?: ""
                    SAP = true

                }

                ItemCRUD.insertItem(item)
            }
            _uiState.update { currentState ->
                currentState.copy(
                    checkItem = true
                )
            }

            Log.e("sync", "actividades sincronizados")
        }
    }

    fun saveConfiguration(context: Context) {
        val urlInt = _uiState.value.urlInt
        val urlExt = _uiState.value.urlExt
        val login = _uiState.value.login
        val password = _uiState.value.password
        val dataBase = _uiState.value.dataBase
        val url = _uiState.value.urlCheck
        val urlTipCheck = _uiState.value.urlTipCheck
        val dataConfiguration =
            ConfigurationApplication(login, password, dataBase, url, urlTipCheck)
        val gson = Gson()
        val jsonData: String = gson.toJson(dataConfiguration)

        val sharedPref = context?.getSharedPreferences("configuration", Context.MODE_PRIVATE)
        val dataConfig = sharedPref?.edit()
        dataConfig?.putString("configuration", jsonData)
        dataConfig?.apply()
        Config.initConfig(context)

        //val jsonStringRecuperada = sharedPref?.getString("configuration", null)
        //val miObjetoRecuperado = gson.fromJson(jsonStringRecuperada, ConfigurationApplication::class.java)
        _uiState.update { currentState ->
            currentState.copy(
                message = true,
                text = "Configuración guardada con éxito.",
                progress = false
            )
        }

    }

    fun initData(context: Context) {
        val gson = Gson()
        val sharedPref = context?.getSharedPreferences("configuration", Context.MODE_PRIVATE)
        val json = sharedPref?.getString("configuration", null)
        if (!json.isNullOrEmpty()) {
            val dataConfig = gson.fromJson(json, ConfigurationApplication::class.java)
            if (dataConfig.urlTipCheck == "Int") {
                _uiState.update { currentState ->

                    currentState.copy(
                        urlInt = dataConfig.url,
                        login = dataConfig.login,
                        password = dataConfig.password,
                        dataBase = dataConfig.dataBase,
                        init = false
                    )
                }
            } else if (dataConfig.urlTipCheck == "Ext") {
                _uiState.update { currentState ->
                    currentState.copy(
                        urlExt = dataConfig.url,
                        login = dataConfig.login,
                        password = dataConfig.password,
                        dataBase = dataConfig.dataBase,
                        init = false
                    )
                }
            }

        }
    }

    private fun deleteAndInsertPriceList() {
        viewModelScope.launch(Dispatchers.IO) {
            var priceList = PriceListObj.getPriceLists(Config.rulUse)
            if (priceList is PriceList) {
                PriceListForListCRUD.deleteAll()
                priceList.value.forEach { element ->
                    val d = PriceListRealm().apply {
                        this.PriceListName = element.PriceListName
                        this.Active = element.Active
                        this.BasePriceList = element.BasePriceList.toString()
                        this.PriceListNo = element.PriceListNo.toString()
                    }
                    PriceListForListCRUD.insert(
                        d
                    )
                }
                _uiState.update { currentState ->
                    currentState.copy(
                        checkPriceLists = true
                    )
                }

                Log.e("sync", "lista de precios  sincronizados")
            }
        }
    }

    fun changecheckBoxClients() {
        if (_uiState.value.checkBoxClients) {
            _uiState.update { currentState ->
                currentState.copy(
                    checkBoxClients = false
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    checkBoxClients = true,
                    checkBoxTodo = false
                )
            }
        }
    }

    fun changecheckBoxOrders() {
        if (_uiState.value.checkBoxOrders) {
            _uiState.update { currentState ->
                currentState.copy(
                    checkBoxOrders = false
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    checkBoxOrders = true,
                    checkBoxTodo = false
                )
            }
        }
    }

    fun changecheckBoxUDO() {
        if (_uiState.value.checkBoxUDO) {
            _uiState.update { currentState ->
                currentState.copy(
                    checkBoxUDO = false
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    checkBoxUDO = true,
                    checkBoxTodo = false
                )
            }
        }
    }

    fun changecheckBoxItems() {
        if (_uiState.value.checkBoxItems) {
            _uiState.update { currentState ->
                currentState.copy(
                    checkBoxItems = false
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    checkBoxItems = true,
                    checkBoxTodo = false
                )
            }
        }
    }

    fun changecheckBoxActivity() {
        if (_uiState.value.checkBoxActivity) {
            _uiState.update { currentState ->
                currentState.copy(
                    checkBoxActivity = false
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    checkBoxActivity = true,
                    checkBoxTodo = false,
                )
            }
        }
    }

    fun changecheckBoxTodo() {
        if (_uiState.value.checkBoxTodo) {
            _uiState.update { currentState ->
                currentState.copy(
                    checkBoxTodo = false,
                    checkBoxClients = false,
                    checkBoxActivity = false,
                    checkBoxOrders = false,
                    checkBoxUDO = false,
                    checkBoxItems = false
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    checkBoxTodo = true,
                    checkBoxClients = true,
                    checkBoxActivity = true,
                    checkBoxOrders = true,
                    checkBoxUDO = true,
                    checkBoxItems = true
                )
            }
        }
    }
}