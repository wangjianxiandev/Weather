package com.wjx.android.weather.model

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/14 10:53
 */
data class GaoDeCode(
    val info: String,
    val infocode: String,
    val regeocode: Regeocode,
    val status: String
)

data class Regeocode(
    val addressComponent: AddressComponent,
    val formatted_address: String
)

data class AddressComponent(
    val adcode: String,
    val building: Building,
    val businessAreas: List<List<Any>>,
    val city: List<Any>,
    val citycode: String,
    val country: String,
    val district: List<Any>,
    val neighborhood: Neighborhood,
    val province: String,
    val seaArea: String,
    val streetNumber: StreetNumber,
    val towncode: List<Any>,
    val township: List<Any>
)

data class Building(
    val name: List<Any>,
    val type: List<Any>
)

data class Neighborhood(
    val name: List<Any>,
    val type: List<Any>
)

data class StreetNumber(
    val direction: List<Any>,
    val distance: List<Any>,
    val number: List<Any>,
    val street: List<Any>
)