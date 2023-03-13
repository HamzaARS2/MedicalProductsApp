package com.ars.data.repository

import android.content.SharedPreferences
import com.ars.domain.model.Customer

class LocalCustomerRepository(private val sp: SharedPreferences) {

    companion object {
        private const val ID_KEY = "customer_id"
        private const val DOC_ID_KEY = "customer_doc_id"
        private const val NAME_KEY = "customer_name"
        private const val EMAIL_KEY = "customer_email"
        private const val PHONE_KEY = "customer_phone"
        private const val ADDRESS_KEY = "customer_address"
    }

    fun getLocalCustomer(): Customer? {
        val id = sp.getInt(ID_KEY, 0)
        val docId = sp.getString(DOC_ID_KEY, null) ?: return null
        val name = sp.getString(NAME_KEY, "")!!
        val email = sp.getString(EMAIL_KEY, "")!!
        val phone = sp.getString(PHONE_KEY, "")!!
        val address = sp.getString(ADDRESS_KEY, "")!!

        return Customer(id, docId, name, email, phone, address)
    }

    fun saveLocalCustomer(customer: Customer?) {
        customer ?: return
        customer.id ?: return
        val editor = sp.edit()
        editor.run {
            putInt(ID_KEY, customer.id!!)
            putString(DOC_ID_KEY, customer.docId)
            putString(NAME_KEY, customer.name)
            putString(EMAIL_KEY, customer.email)
            putString(PHONE_KEY, customer.phone)
            putString(ADDRESS_KEY, customer.address)
            apply()
        }
    }
}