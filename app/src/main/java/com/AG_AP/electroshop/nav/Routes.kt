package com.AG_AP.electroshop.nav

enum class Routes(val route: String) {
    ScreenLogin(route = "LoginScreen"),
    ScreenConfig(route = "SettingScreen"),
    ScreenMenu(route = "MenuScreen"),

    MixedActivityScreen(route = "MixedActivityScreen"),
    MixedBusinessPartnerScreen(route = "MixedBusinessPartnerScreen"),
    MixedOrderScreen(route = "MixedOrderScreen"),
    MixedPurchaseOrderScreen(route = "MixedPurchaseOrderScreen"),
    MixedItemScreen(route = "MixedItemScreen"),

    ScreenActivity(route = "ActivityScreen"),
    ScreenActivityAux(route = "ActivityScreen"),
    ListActivity(route = "ListActivity"),

    BusinessPartner(route = "BusinessPartner"),
    ScreenBusinessPartnerList(route = "BusinessPartnerList"),
    BusinessPartnerAux(route = "BusinessPartner"),
    BusinessPartnerUltimate(route = "BusinessPartnerUltimate"),

    ScreenOrder(route = "Order"),
    ScreenOrderList(route = "OrderList"),
    ScreenOrderAux(route = "Order"),

    PurchaseOrderScreen(route = "PurchaseOrderScreen"),
    PurchaseOrderList(route = "PurchaseOrderList"),
    PurchaseOrderAux(route = "PurchaseOrderScreen"),

    ItemScreen(route = "ItemScreen"),
    ItemScreenList(route = "ItemScreenList"),
    ItemScreenAux(route = "ItemScreen")

}