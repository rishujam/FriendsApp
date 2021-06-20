package store.cru.crushcheck.models

data class PrivateMessage(
    val message:String="",
    val to:String="",
    val time:Int,
    val from:String
)