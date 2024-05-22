package com.AG_AP.electroshop.firebase

import android.annotation.SuppressLint
import android.util.Log
import com.AG_AP.electroshop.firebase.models.Activity
import com.AG_AP.electroshop.firebase.models.Price

object ActivityCRUD {

    @SuppressLint("StaticFieldLeak")
    var database: FirebaseFirestore = DatabaseInitializer.database

    val coleccion = "SEIactividades"

    fun insertActivity(activity : Activity) {

        this.database
            .collection(this.coleccion)
            .document(activity.ClgCode.toString())
            .set(activity.toHashMap())
            .addOnSuccessListener {
                Log.e("ActivityFireBase", "Creado Actividad: ${it.toString()}")
            }
            .addOnFailureListener { e ->
                Log.w("ActivityFireBase", "Error añadiendo el documento $e")
            }
    }

    fun insertActivityForFireBase(activity : Activity) {
        val docRef = this.database
            .collection(this.coleccion)
            .document()

        val newActivity = activity.copy(idFireBase = docRef.id) // Guardar el ID generado dentro de la actividad

        docRef
            .set(newActivity.toHashMap())
            .addOnSuccessListener {
                Log.e("ActivityFireBase", "Creado Actividad con ID: ${docRef.id}")
            }
            .addOnFailureListener { e ->
                Log.w("ActivityFireBase", "Error añadiendo el documento $e")
            }
    }

    fun getActivityById(id: Int,callback:(Activity?)->Unit) {
        this.database
            .collection(this.coleccion)
            .document(id.toString())
            .get()
            .addOnSuccessListener {
                if (it.exists()) {
                    val dataActivity = it.data
                    Log.e("Pruebas", "Datos: ${dataActivity.toString()}")


                    val idFireBase = dataActivity?.get("idFireBase").toString()
                    val nota = dataActivity?.get("nota").toString()
                    val ActivityDate = dataActivity?.get("ActivityDate").toString()
                    val ActivityTime = dataActivity?.get("ActivityTime").toString()
                    val CardCode = dataActivity?.get("CardCode").toString()
                    val EndTime = dataActivity?.get("EndTime").toString()
                    val Action = dataActivity?.get("Action").toString()
                    val Tel = dataActivity?.get("Tel").toString()
                    val ClgCode = dataActivity?.get("ClgCode").toString()
                    val Priority = dataActivity?.get("Priority").toString()
                    val U_SEIPEDIDOCOMPRAS = dataActivity?.get("U_SEIPEDIDOCOMPRAS").toString().toInt()
                    val U_SEIPEDIDOCLIENTE = dataActivity?.get("U_SEIPEDIDOCLIENTE").toString().toInt()
                    val SAP = dataActivity?.get("SAP").toString().toBoolean()
                    val dataReturn = Activity(
                        idFireBase,
                        nota,
                        ActivityDate,
                        ActivityTime,
                        CardCode,
                        EndTime,
                        Action,
                        Tel,
                        ClgCode,
                        Priority,
                        U_SEIPEDIDOCOMPRAS,
                        U_SEIPEDIDOCLIENTE,
                        SAP
                    )
                    callback(dataReturn)
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener {
                Log.e("Errores", "Error en get precio por id, posiblemente no exista $it")
            }
    }

    fun getAllActivity(callback: (MutableList<Activity>) -> Unit) {
        this.database
            .collection(this.coleccion)
            .get()
            .addOnSuccessListener {
                    lista ->
                val ActivityList = mutableListOf<Activity>()

                for (document in lista.documents) {
                    val dataActivity = document.data
                    val idFireBase = dataActivity?.get("idFireBase").toString()
                    val nota = dataActivity?.get("nota").toString()
                    val ActivityDate = dataActivity?.get("ActivityDate").toString()
                    val ActivityTime = dataActivity?.get("ActivityTime").toString()
                    val CardCode = dataActivity?.get("CardCode").toString()
                    val EndTime = dataActivity?.get("EndTime").toString()
                    val Action = dataActivity?.get("Action").toString()
                    val Tel = dataActivity?.get("Tel").toString()
                    val ClgCode = dataActivity?.get("ClgCode").toString()
                    val Priority = dataActivity?.get("Priority").toString()
                    val U_SEIPEDIDOCOMPRAS = dataActivity?.get("U_SEIPEDIDOCOMPRAS").toString().toInt()
                    val U_SEIPEDIDOCLIENTE = dataActivity?.get("U_SEIPEDIDOCLIENTE").toString().toInt()
                    val SAP = dataActivity?.get("SAP").toString().toBoolean()
                    val dataReturn: Activity = Activity(
                        idFireBase,
                        nota,
                        ActivityDate,
                        ActivityTime,
                        CardCode,
                        EndTime,
                        Action,
                        Tel,
                        ClgCode,
                        Priority,
                        U_SEIPEDIDOCOMPRAS,
                        U_SEIPEDIDOCLIENTE,
                        SAP
                    )

                    ActivityList.add(dataReturn)
                }

                callback(ActivityList)
            }
            .addOnFailureListener {
                Log.e("Errores", "Error en get Actividades, posiblemente vacio $it")
                callback(mutableListOf())
            }
    }

    fun updateActivityById(data:Activity) {
        this.database
            .collection(this.coleccion)
            .document(data.ClgCode.toString())
            .update(data.toHashMap())
            .addOnSuccessListener {
                Log.e("Pruebas", "Updateado la actividad con id: ${data.ClgCode}")
            }
            .addOnFailureListener {
                Log.e("Errores", "Error  la actividad por id $it")
            }
    }

    fun deleteActivityById(id: String) {
        this.database
            .collection(this.coleccion)
            .document(id)
            .delete()
            .addOnSuccessListener {
                Log.e("Pruebas", "Borrado el activity con id: $id")
            }
            .addOnFailureListener {
                Log.e("Errores", "Error en delete activity por id $it")
            }
    }

    fun filterForCardCode(cardCode: String,callback:(MutableList<Activity>)->Unit){
        this.database
            .collection(this.coleccion)
            .whereEqualTo("CardCode",cardCode)
            .get()
            .addOnSuccessListener {
                    lista ->
                val ActivityList = mutableListOf<Activity>()

                for (document in lista.documents) {
                    val dataActivity = document.data
                    val idFireBase = dataActivity?.get("idFireBase").toString()
                    val nota = dataActivity?.get("nota").toString()
                    val ActivityDate = dataActivity?.get("ActivityDate").toString()
                    val ActivityTime = dataActivity?.get("ActivityTime").toString()
                    val CardCode = dataActivity?.get("CardCode").toString()
                    val EndTime = dataActivity?.get("EndTime").toString()
                    val Action = dataActivity?.get("Action").toString()
                    val Tel = dataActivity?.get("Tel").toString()
                    val ClgCode = dataActivity?.get("ClgCode").toString()
                    val Priority = dataActivity?.get("Priority").toString()
                    val U_SEIPEDIDOCOMPRAS = dataActivity?.get("U_SEIPEDIDOCOMPRAS").toString().toInt()
                    val U_SEIPEDIDOCLIENTE = dataActivity?.get("U_SEIPEDIDOCLIENTE").toString().toInt()
                    val SAP = dataActivity?.get("SAP").toString().toBoolean()
                    val dataReturn: Activity = Activity(
                        idFireBase,
                        nota,
                        ActivityDate,
                        ActivityTime,
                        CardCode,
                        EndTime,
                        Action,
                        Tel,
                        ClgCode,
                        Priority,
                        U_SEIPEDIDOCOMPRAS,
                        U_SEIPEDIDOCLIENTE,
                        SAP
                    )

                    ActivityList.add(dataReturn)
                }

                callback(ActivityList)
            }
            .addOnFailureListener {
                Log.e("Errores", "Error en get Actividades, posiblemente vacio $it")
                callback(mutableListOf())
            }

    }
}