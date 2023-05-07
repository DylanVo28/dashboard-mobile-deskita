package com.example.admin_deskita.entity

import java.util.*
import kotlin.collections.ArrayList

class Discount(
    var _id: String,
    var name: String,
    var categoryProduct: String,
    var validDate: String,
    var quantity: Int,
    var value: Int,
    var createAt: String,
    var used: Boolean){


}