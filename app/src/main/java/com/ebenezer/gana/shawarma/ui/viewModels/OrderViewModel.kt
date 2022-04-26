package com.ebenezer.gana.shawarma.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

private const val PRICE_PER_SHAWARMA = 20.00
private const val PRICE_FOR_SAME_DAY_PICKUP = 30.00

class OrderViewModel : ViewModel() {

    private val _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> = _quantity


    private val _flavor = MutableLiveData<String>()
    val flavor: LiveData<String> = _flavor

    private val _fullname = MutableLiveData<String>()
    val fullname:LiveData<String> = _fullname

    private val _address = MutableLiveData<String>()
    val address:LiveData<String> = _address

    private val _phone = MutableLiveData<String>()
    val phone:LiveData<String> = _phone


    val dateOptions: List<String> = getPickupOptions()

    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    private val _price = MutableLiveData<Double>()
    val price: LiveData<String> = Transformations.map(_price) {
        "N$it"
    }


    init {
        resetOrder()
    }

    fun setContactDetails(name:String, address:String, phone:String){
        _fullname.value = name
        _address.value = address
        _phone.value = phone
    }

    fun isEntryValid(name:String, address: String, phone: String):Boolean{
        if (name.isBlank() || address.isBlank()|| phone.isBlank()){
            return false
        }
        return true
    }

    fun setQuantity(numberOfShawarma: Int) {
        _quantity.value = numberOfShawarma
        updatePrice()
    }

    fun setFlavor(flavor: String) {
        _flavor.value = flavor
    }

    fun setDate(pickupDate: String) {
        _date.value = pickupDate
        updatePrice()
    }

    fun hasNoFlavorSet(): Boolean {
        return _flavor.value.isNullOrEmpty()
    }

    private fun updatePrice() {
        var calculatedPrice = (quantity.value ?: 0) * PRICE_PER_SHAWARMA

        if (dateOptions[0] == _date.value) {
            calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
        }
        _price.value = calculatedPrice
    }


    private fun getPickupOptions(): List<String> {
        val options = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        repeat(4) {
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return options
    }


    fun resetOrder() {
        _quantity.value = 0
        _flavor.value = ""
        _date.value = dateOptions[0]
        _price.value = 0.0
    }
}