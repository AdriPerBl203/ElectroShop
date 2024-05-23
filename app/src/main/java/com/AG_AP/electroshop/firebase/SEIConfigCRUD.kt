package com.AG_AP.electroshop.firebase

import android.annotation.SuppressLint
import android.util.Log
import com.AG_AP.electroshop.firebase.models.OrderFireBase
import com.AG_AP.electroshop.firebase.models.SEIConfig
import io.realm.kotlin.ext.query

object SEIConfigCRUD : ActionFirebase {
    /*

    @SuppressLint("StaticFieldLeak")
    var database: FirebaseFirestore = DatabaseInitializer.database

    private val coleccion = "SEIConfig"

    suspend fun insertSEIConfig(config: SEIConfig) {
        database
            .collection(coleccion)
            .document(config.U_name)
            .set(config.toHashMap())
            .addOnSuccessListener {
                Log.e("Pruebas", "Introducido en SEIConfig ${config.toString()}")
            }
            .addOnFailureListener {
                Log.e("Errores", "Error insertando en SEIConfig ${it.printStackTrace()}")
            }
    }

    fun getSEIConfigById(idSEIConfig: String, callback: (SEIConfig?) -> Unit) {
        database
            .collection(coleccion)
            .document(idSEIConfig)
            .get()
            .addOnSuccessListener {
                if (it.exists()) {
                    val datosSEIConfig = it.data

                    val code = datosSEIConfig?.get("Code").toString().toInt()
                    val U_Empleado = datosSEIConfig?.get("U_Empleado").toString().toInt()
                    val U_name = datosSEIConfig?.get("U_name") as String
                    val U_password = datosSEIConfig["U_password"] as String
                    val U_articulo = datosSEIConfig["U_articulo"] as String
                    val U_actividad = datosSEIConfig["U_actividad"] as String
                    val U_PedidoCI = datosSEIConfig["U_PedidoCI"] as String
                    val U_PedidoCO = datosSEIConfig["U_PedidoCO"] as String

                    val config = SEIConfig(
                        code,
                        U_Empleado,
                        U_name,
                        U_password,
                        U_articulo,
                        U_actividad,
                        U_PedidoCI,
                        U_PedidoCO
                    )

                    callback(config)
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener {
                Log.e(
                    "Errores",
                    "Error obteniendo SEIConfig con Id: $idSEIConfig, ${it.printStackTrace()}"
                )
            }
    }

    suspend fun getAllSEIConfig(callback: (MutableList<SEIConfig>?) -> Unit) {
        database
            .collection(coleccion)
            .get()
            .addOnSuccessListener { listaDatos ->
                val listaConfig = mutableListOf<SEIConfig>()

                for (config in listaDatos) {
                    val datos = config.data

                    val code = datos["Code"].toString().toInt()
                    val U_Empleado = datos["U_Empleado"].toString().toInt()
                    val U_name = datos["U_name"] as String
                    val U_password = datos["U_password"] as String
                    val U_articulo = datos["U_articulo"] as String
                    val U_actividad = datos["U_actividad"] as String
                    val U_PedidoCI = datos["U_PedidoCI"] as String
                    val U_PedidoCO = datos["U_PedidoCO"] as String


                    listaConfig.add(
                        SEIConfig(
                            code,
                            U_Empleado,
                            U_name,
                            U_password,
                            U_articulo,
                            U_actividad,
                            U_PedidoCI,
                            U_PedidoCO
                        )
                    )
                }

                callback(listaConfig)
            }
            .addOnFailureListener {
                Log.e("Errores", "Error obteniendo todos los datos de SEIConfig, $it")
                callback(mutableListOf())
            }
    }

    fun updateSEIConfigById(idConfig: String, config: SEIConfig) {
        database
            .collection(coleccion)
            .document(idConfig)
            .update(config.toHashMap())
            .addOnSuccessListener {
                Log.e("Pruebas", "Actualizado SEIConfig con id: $idConfig")
            }
            .addOnFailureListener {
                Log.e("Errores", "Ha ocurrido un error actualizando SEIConfig con id: $idConfig")
            }
    }

     suspend fun deleteSEIConfigById(idConfig: String) {
        database
            .collection(coleccion)
            .document(idConfig)
            .delete()
            .addOnSuccessListener {
                Log.e("Pruebas", "Borrado SEIConfig con id: $idConfig")
            }
            .addOnFailureListener {
                Log.e("Errores", "Ha ocurrido un error borrando SEIConfig con id: $idConfig")
            }
    }

    suspend fun getU_nameTheUser():MutableList<String?>{
        var uNameList = mutableListOf<String?>()
        this.getAllSEIConfig { element->
            if(element is MutableList<SEIConfig>){
                element.forEach { element->
                    uNameList.add(element.U_name)
                }
            }
        }
        return uNameList
    }


  */


    val realm = DatabaseInitializer.realm

    override fun insert(data: Any) {
        val sieconfig = data as SEIConfig

        realm.writeBlocking {
            copyToRealm(sieconfig)
        }
    }

    override fun getObjectById(id: Int, callback: (Any?) -> Unit) {
        val byId =
            realm.query<SEIConfig>("idFireBase = $0", id.toString()).first().find() as SEIConfig
        callback(byId)

    }

    override fun getObjectByIdToString(id: String, callback: (Any?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getAllObject(callback: (MutableList<*>?) -> Unit) {
        val all = realm.query<SEIConfig>().find() as MutableList<*>?
        callback(all)
    }

    override suspend fun updateObjectById(data: Any) {
        val sEIConfig = data as SEIConfig
        realm.query<SEIConfig>("Code = $0", sEIConfig.Code)
            .first()
            .find()
            ?.also { oldActivity ->
                realm.write {
                    findLatest(oldActivity)?.let { it ->
                        it.Code = sEIConfig.Code
                        it.U_Empleado = sEIConfig.U_Empleado
                        it.U_name = sEIConfig.U_name
                        it.U_password = sEIConfig.U_password
                        it.U_articulo = sEIConfig.U_articulo
                        it.U_actividad = sEIConfig.U_actividad
                        it.U_PedidoCI = sEIConfig.U_PedidoCI
                        it.U_PedidoCO = sEIConfig.U_PedidoCO
                    }
                }
            }
    }

    override suspend fun deleteObjectById(id: String) {
        val deleteObejct = realm.query<SEIConfig>("Code = $0", id)
        realm.writeBlocking {
            delete(deleteObejct)
        }
    }

}