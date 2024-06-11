package com.AG_AP.electroshop.realm

import com.AG_AP.electroshop.realm.models.Activity
import io.realm.kotlin.delete
import io.realm.kotlin.ext.query

object ActivityCRUD {

    val realm = DatabaseInitializer.realm

    fun insertActivity(activity: Activity) {
        realm.writeBlocking {
            copyToRealm(activity)
        }
    }

    fun insertActivityForFireBase(activity: Activity) {
        TODO()
    }

    fun getActivityById(id: Int, callback: (Activity?) -> Unit) {
        val byId =
            realm.query<Activity>("ClgCode = $0", id.toString()).find().first() as Activity
        callback(byId)
    }

    fun getAllActivity(callback: (MutableList<Activity>) -> Unit) {
        val all = realm.query<Activity>().find()
        callback(all.toMutableList())
    }

    suspend fun updateActivityById(data: Activity) {
        val activity = data
        realm.query<Activity>("ClgCode = $0", activity.ClgCode)
            .first()
            .find()
            ?.also { oldActivity ->
                realm.write {
                    findLatest(oldActivity)?.let { it ->
                        it.idFireBase = activity.idFireBase
                        it.nota = activity.nota
                        it.ActivityTime = activity.ActivityTime
                        it.ActivityDate = activity.ActivityDate
                        it.CardCode = activity.CardCode
                        it.EndTime = activity.EndTime
                        it.Action = activity.Action
                        it.Tel = activity.Tel
                        it.ClgCode = activity.ClgCode
                        it.Priority = activity.Priority
                        it.U_SEIPEDIDOCOMPRAS = activity.U_SEIPEDIDOCOMPRAS
                        it.U_SEIPEDIDOCLIENTE = activity.U_SEIPEDIDOCLIENTE
                        it.SAP = activity.SAP

                    }
                }
            }
    }

    fun deleteActivityById(id: String) {
        val activityToDel = realm.query<Activity>("ClgCode == $0", id).find().firstOrNull()
        if (activityToDel != null) {
            realm.writeBlocking {
                findLatest(activityToDel)
                    ?.also { delete(it) }
            }
        }
    }

    fun filterForCardCode(cardCode: String, callback: (MutableList<Activity>) -> Unit) {
        val byId =
            realm.query<Activity>("CardCode = $0", cardCode).find()
        callback(byId.toMutableList())
    }

    fun deleteAll() {
        realm.writeBlocking {
            delete<Activity>()
        }
    }
}