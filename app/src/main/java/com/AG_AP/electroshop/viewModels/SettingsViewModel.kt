package com.AG_AP.electroshop.viewModels

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.AG_AP.electroshop.endpoints.models.activity.Activity
import com.AG_AP.electroshop.endpoints.models.businessPartners.BusinessPartners
import com.AG_AP.electroshop.endpoints.models.login.Login
import com.AG_AP.electroshop.endpoints.objects.ActivityObj
import com.AG_AP.electroshop.endpoints.objects.BusinessPartnersObj
import com.AG_AP.electroshop.endpoints.objects.LoginObj
import com.AG_AP.electroshop.endpoints.udo.models.CreateField
import com.AG_AP.electroshop.endpoints.udo.models.createUdo.CreateUdo
import com.AG_AP.electroshop.endpoints.udo.models.createUdo.UserObjectMDFindColumn
import com.AG_AP.electroshop.endpoints.udo.models.createUdo.UserObjectMDFormColumn
import com.AG_AP.electroshop.endpoints.udo.models.createUserUDO.CreateUserUDO
import com.AG_AP.electroshop.endpoints.udo.models.getUserUdo.SeiConfigUser
import com.AG_AP.electroshop.endpoints.udo.objects.UDOobj
import com.AG_AP.electroshop.firebase.ActivityCRUD
import com.AG_AP.electroshop.firebase.BusinessPartnerCRUD
import com.AG_AP.electroshop.firebase.SEIConfigCRUD
import com.AG_AP.electroshop.firebase.models.BusinessPartner
import com.AG_AP.electroshop.firebase.models.SEIConfig
import com.AG_AP.electroshop.functions.Config
import com.AG_AP.electroshop.functions.ConfigurationApplication
import com.AG_AP.electroshop.functions.validarURL
import com.AG_AP.electroshop.uiState.SettingUiState
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
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
            if(data){
                text ="Test realizado con éxito."
                _uiState.update { currentState -> currentState.copy(
                    message = true,
                    text = text,
                    progress = false,
                    ButtomEnable = true
                ) }
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
            message = false
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
        }
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
                        element.CardCode,
                        element.CardType,
                        element.CardName,
                        phone1,
                        email,

                    ))
                }
            }
        }
    }

    private fun deleteAndInsertActivity() {
        viewModelScope.launch(Dispatchers.IO) {
            val activities : Activity? = ActivityObj.getActivities(Config.rulUse)
            if(activities is Activity){
                activities.value.forEach{element ->
                    ActivityCRUD.deleteActivityById(element.ActivityCode.toString())
                }
                activities.value.forEach{element ->
                    val activity : com.AG_AP.electroshop.firebase.models.Activity = com.AG_AP.electroshop.firebase.models.Activity(
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
                    )
                    ActivityCRUD.insertActivity(activity)
                }
            }
        }
    }

    fun sync() {
        _uiState.update { currentState -> currentState.copy(
            progress = true,
        ) }
        deleteAndInsertUserUdo()
        deleteAndInsertBusinessPartner()
        deleteAndInsertActivity()

        Log.e("JOSELETE","AAAa")

        _uiState.update { currentState -> currentState.copy(
            message = true,
            text = "Sincronización realizada",
            progress = false
        ) }

        //traer articulos, clientes, pedidos....
        /* val items = ItemObj.getItems(Config.rulUse)
         val orders = OrdersObj.getOrders(Config.rulUse)
         val pruchaseOrders = PurchaseOrdersObj.getPurchaseOrders(Config.rulUse)
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

        val jsonStringRecuperada = sharedPref?.getString("configuration", null)
        val miObjetoRecuperado = gson.fromJson(jsonStringRecuperada, ConfigurationApplication::class.java)

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
            ) }
        }

    }
}