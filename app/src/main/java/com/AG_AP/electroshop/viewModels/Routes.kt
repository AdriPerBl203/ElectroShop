package com.AG_AP.electroshop.viewModels

enum class Routes(val route: String) {
    ScreenLogin(route = "LoginScreen"),
    ScreenConfig(route = "SettingScreen"),
    ScreenMenu(route = "MenuScreen"),

    ScreenActivity(route = "ActivityScreen"),
    ScreenActivityAux(route = "ActivityScreen"),
    ListActivity(route = "ListActivity"),

    BusinessPartner(route = "BusinessPartner"),
    ScreenBusinessPartnerList(route = "BusinessPartnerList"),
    BusinessPartnerAux(route = "BusinessPartner"),

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