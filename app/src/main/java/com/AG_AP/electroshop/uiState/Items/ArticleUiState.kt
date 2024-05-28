package com.AG_AP.electroshop.uiState.Items

data class ArticleUiState(
    val LineNum: Int = 0,
    val ItemCode: String = "",
    val ItemDescription: String = "",
    val Quantity: Double = 0.0,
    val Price: Double = 0.0,
    val DiscountPercent: Double = 0.0
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ArticleUiState

        if (ItemCode != other.ItemCode) return false
        if (ItemDescription != other.ItemDescription) return false
        if (Quantity != other.Quantity) return false
        if (Price != other.Price) return false
        if (DiscountPercent != other.DiscountPercent) return false

        return true
    }

    override fun hashCode(): Int {
        var result = LineNum
        result = 31 * result + ItemCode.hashCode()
        result = 31 * result + ItemDescription.hashCode()
        result = 31 * result + Quantity.hashCode()
        result = 31 * result + Price.hashCode()
        result = 31 * result + DiscountPercent.hashCode()
        return result
    }
}
