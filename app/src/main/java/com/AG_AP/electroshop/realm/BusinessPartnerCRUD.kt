package com.AG_AP.electroshop.realm

import com.AG_AP.electroshop.realm.models.BusinessPartner
import io.realm.kotlin.ext.query

object BusinessPartnerCRUD : ActionRealm {
    /*
    @SuppressLint("StaticFieldLeak")
    var database: FirebaseFirestore = DatabaseInitializer.database

    val coleccion = "SEIbusinessPartner"
    override fun insert(data: Any) {
        val dataAux: BusinessPartner
        if(data is BusinessPartner){
            dataAux = data
            this.database
                .collection(this.coleccion)
                .document(dataAux.CardCode.toString())
                .set(dataAux.toHashMap())
                .addOnSuccessListener {
                    Log.e("DataFireBase", "Creado Business partner: ${it.toString()}")
                }
                .addOnFailureListener { e ->
                    Log.w("DataFireBase", "Error añadiendo el documento $e")
                }
        }
    }

    fun insertForFireBase(data: BusinessPartner) {
        val docRef = this.database
            .collection(this.coleccion)
            .document()
        val newdata = data.copy(idFireBase = docRef.id) // Guardar el ID generado dentro de la actividad
        docRef
            .set(newdata.toHashMap())
            .addOnSuccessListener {
                Log.e("FireBase", "Creado Business Partner con ID: ${docRef.id}")
            }
            .addOnFailureListener { e ->
                Log.w("FireBase", "Error añadiendo el documento $e")
            }
    }

    override fun getObjectById(id: Int, callback: (Any?) -> Unit) {
        this.database
            .collection(this.coleccion)
            .document(id.toString())
            .get()
            .addOnSuccessListener {
                if (it.exists()) {
                    val data = it.data
                    Log.e("Pruebas", "Datos: ${data.toString()}")

                    val idFireBase = data?.get("idFireBase").toString()
                    val CardCode = data?.get("CardCode").toString() ?: ""
                    val CardType = data?.get("CardType").toString() ?: ""
                    val CardName = data?.get("CardName").toString() ?: ""
                    val Cellular = data?.get("Cellular").toString() ?: ""
                    val EmailAddress = data?.get("EmailAddress").toString() ?: ""
                    val SAP = data?.get("SAP").toString().toBoolean()

                    val dataReturn: BusinessPartner = BusinessPartner(
                        idFireBase,
                        CardCode,
                        CardType,
                        CardName,
                        Cellular,
                        EmailAddress,
                        SAP
                    )
                    callback(dataReturn)
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener {
                Log.e("Errores", "Error en get business partner por id, posiblemente no exista $it")
            }
    }

    override fun getObjectByIdToString(id: String, callback: (Any?) -> Unit) {
        this.database
            .collection(this.coleccion)
            .document(id)
            .get()
            .addOnSuccessListener {
                if (it.exists()) {
                    val data = it.data
                    Log.e("Pruebas", "Datos: ${data.toString()}")

                    val idFireBase = data?.get("idFireBase").toString()
                    val CardCode = data?.get("CardCode").toString() ?: ""
                    val CardType = data?.get("CardType").toString() ?: ""
                    val CardName = data?.get("CardName").toString() ?: ""
                    val Cellular = data?.get("Cellular").toString() ?: ""
                    val EmailAddress = data?.get("EmailAddress").toString() ?: ""
                    val SAP = data?.get("SAP").toString().toBoolean()

                    val dataReturn: BusinessPartner = BusinessPartner(
                        idFireBase,
                        CardCode,
                        CardType,
                        CardName,
                        Cellular,
                        EmailAddress,
                        SAP
                    )
                    callback(dataReturn)
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener {
                Log.e("Errores", "Error en get business partner por id, posiblemente no exista $it")
            }
    }

    fun getBPByName(name: String, sap: Boolean, callback: (List<BusinessPartner?>) -> Unit) {
        this.database
            .collection(this.coleccion)
            .whereEqualTo("CardName", name)
            .whereEqualTo("SAP", sap)
            .get()
            .addOnSuccessListener {
                    lista ->
                val dataList = mutableListOf<BusinessPartner>()

                for (document in lista.documents) {
                    val data = document.data

                    val idFireBase = data?.get("idFireBase").toString()
                    val CardCode = data?.get("CardCode").toString()
                    val CardType = data?.get("CardType").toString()
                    val CardName = data?.get("CardName").toString()
                    val Cellular = data?.get("Cellular").toString()
                    val EmailAddress = data?.get("EmailAddress").toString()
                    val SAP = data?.get("SAP").toString().toBoolean()

                    val dataReturn = BusinessPartner(
                        idFireBase,
                        CardCode,
                        CardType,
                        CardName,
                        Cellular,
                        EmailAddress,
                        SAP
                    )

                    dataList.add(dataReturn)
                }

                callback(dataList)
            }
            .addOnFailureListener {
                Log.e("Errores", "Error en get business partner por name $it")
            }
    }

    fun getBPBySAP(sap: Boolean, callback: (List<BusinessPartner?>) -> Unit) {
        this.database
            .collection(this.coleccion)
            .whereEqualTo("SAP", sap)
            .get()
            .addOnSuccessListener {
                    lista ->
                val dataList = mutableListOf<BusinessPartner>()
                for (document in lista.documents) {
                    val data = document.data
                    Log.e("Pruebas", document.data.toString())

                    val idFireBase = data?.get("idFireBase").toString()
                    val CardCode = data?.get("CardCode").toString()
                    val CardType = data?.get("CardType").toString()
                    val CardName = data?.get("CardName").toString()
                    val Cellular = data?.get("Cellular").toString()
                    val EmailAddress = data?.get("EmailAddress").toString()
                    val SAP = data?.get("SAP").toString().toBoolean()

                    val dataReturn = BusinessPartner(
                        idFireBase,
                        CardCode,
                        CardType,
                        CardName,
                        Cellular,
                        EmailAddress,
                        SAP
                    )

                    dataList.add(dataReturn)
                }

                callback(dataList)
            }
            .addOnFailureListener {
                Log.e("Errores", "Error en get business partner por name $it")
            }
    }

    override fun getAllObject(callback: (MutableList<*>?) -> Unit) {
        this.database
            .collection(this.coleccion)
            .get()
            .addOnSuccessListener {
                    lista ->
                val dataList = mutableListOf<BusinessPartner>()

                for (document in lista.documents) {
                    val data = document.data

                    val idFireBase = data?.get("idFireBase").toString()
                    val CardCode = data?.get("CardCode").toString()
                    val CardType = data?.get("CardType").toString()
                    val CardName = data?.get("CardName").toString()
                    val Cellular = data?.get("Cellular").toString()
                    val EmailAddress = data?.get("EmailAddress").toString()
                    val SAP = data?.get("SAP").toString().toBoolean()

                    val dataReturn: BusinessPartner = BusinessPartner(
                        idFireBase,
                        CardCode,
                        CardType,
                        CardName,
                        Cellular,
                        EmailAddress,
                        SAP
                    )

                    dataList.add(dataReturn)
                }

                callback(dataList)
            }
            .addOnFailureListener {
                Log.e("Errores", "Error en get BusinessPartner, posiblemente vacio $it")
                callback(null)
            }
    }

    override fun updateObjectById(data: Any) {
        val dataAux: BusinessPartner
        if(data is BusinessPartner){
            dataAux = data
            this.database
                .collection(this.coleccion)
                .document(dataAux.CardCode.toString())
                .update(dataAux.toHashMap())
                .addOnSuccessListener {
                    Log.e("Pruebas", "Updateado el cliente con id: ${dataAux.CardCode}")
                }
                .addOnFailureListener {
                    Log.e("Errores", "Error  la actividad por id $it")
                }
        }
    }

    override suspend fun deleteObjectById(id: String) {
        this.database
            .collection(this.coleccion)
            .document(id)
            .delete()
            .addOnSuccessListener {
                Log.e("Pruebas", "Borrado del cliente con id: $id")
            }
            .addOnFailureListener {
                Log.e("Errores", "Error en delete cliente por id $it")
            }
    }

 */
    val realm = DatabaseInitializer.realm

    override fun insert(data: Any) {
        val BusinessPartner = data as BusinessPartner

        realm.writeBlocking {
            copyToRealm(BusinessPartner)
        }
    }

    override fun getObjectById(id: Int, callback: (Any?) -> Unit) {
        val byId =
            realm.query<BusinessPartner>("CardCode = $0", id.toString()).find().first()
        callback(byId)

    }

    override fun getObjectByIdToString(id: String, callback: (Any?) -> Unit) {
        val byId =
            realm.query<BusinessPartner>("CardCode = $0", id.toString()).find().firstOrNull()
        callback(byId)
    }

    override fun getAllObject(callback: (MutableList<*>?) -> Unit) {
        val all = realm.query<BusinessPartner>().find()
        callback(all.toMutableList())
    }

    override suspend fun updateObjectById(data: Any) {
        val businessPartner = data as BusinessPartner
        realm.query<BusinessPartner>("CardCode = $0", businessPartner.CardCode)
            .first()
            .find()
            ?.also { oldActivity ->
                realm.write {
                    findLatest(oldActivity)?.let { it ->
                        it.idFireBase = businessPartner.idFireBase
                        it.CardCode = businessPartner.CardCode
                        it.CardType = businessPartner.CardType
                        it.CardName = businessPartner.CardName
                        it.Cellular = businessPartner.Cellular
                        it.EmailAddress = businessPartner.EmailAddress
                        it.SAP = businessPartner.SAP
                    }
                }
            }
    }

    override suspend fun deleteObjectById(id: String) {
        val deleteObejct = realm.query<BusinessPartner>("CardCode == $0", id).find().firstOrNull()
        if (deleteObejct != null) {
            realm.writeBlocking {
                findLatest(deleteObejct)
                    ?.also { delete(it) }
            }
        }
    }

    fun deleteAll() {
        val objectListToDelete = realm.query<BusinessPartner>("SAP == $0", true).find()
        if (objectListToDelete.isNotEmpty()) {
            realm.writeBlocking {
                objectListToDelete.forEach {
                    val deleteableObject = findLatest(it)
                    if (deleteableObject != null) {
                        delete(deleteableObject)
                    }
                }
            }
        }

    }


    fun getBPBySAP(sap: Boolean, callback: (List<BusinessPartner?>) -> Unit) {
        val all = realm.query<BusinessPartner>("SAP == $0", sap).find()
        callback(all.toList())
    }

    fun getBPByName(name: String, sap: Boolean, callback: (List<BusinessPartner?>) -> Unit) {
        val all =
            realm.query<BusinessPartner>("SAP == $0", sap).query("CardName == $0", name).find()
        callback(all.toList())
    }

    fun insertForFireBase(data: BusinessPartner) {
        //TODO("Not yet implemented")
        /*val docRef = this.database
            .collection(this.coleccion)
            .document()
        val newdata = data.copy(idFireBase = docRef.id) // Guardar el ID generado dentro de la actividad
        docRef
            .set(newdata.toHashMap())
            .addOnSuccessListener {
                Log.e("FireBase", "Creado Business Partner con ID: ${docRef.id}")
            }
            .addOnFailureListener { e ->
                Log.w("FireBase", "Error añadiendo el documento $e")
            }*/
    }

}