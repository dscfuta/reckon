package com.example.reckon.utils

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reckon.BaseActivity
import com.example.reckon.R
import com.example.reckon.ui.activity.AppEntryPoint
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PrefManager(var context: Context) {

    private lateinit var pref : SharedPreferences
    private lateinit var ingredientsSP: SharedPreferences
    private lateinit var liveStockSp: SharedPreferences

    init {
        getSp()
    }

    companion object {
        //Const Values for SP manipulation -*Fave
        const val LIVESTOCK_KEY = "livestock_key"
        const val INGREDIENT_PREFS_NAME = "Ingredients"
        const val LIVESTOCK_PREFS_NAME = "Livestock"

        const val FISH_LIVE_STOCK = "Fish"
        const val POULTY_LIVE_STOCK = "Poultry"
        const val INGREDIENTS = "ingredientsvalue"
        const val SELECTED_INGREDIENTS = "selectedIngredients"

        const val modifyDialogFragTag = "dialogTag"
    }

    private fun getSp() {
        pref = context.getSharedPreferences(context.getString(R.string.pref_name), Context.MODE_PRIVATE)
    }

    //Getting the unique SharePreferences for Ingredients -*Fave
    private fun getIngredientSp(): SharedPreferences {
        ingredientsSP = context.getSharedPreferences(INGREDIENT_PREFS_NAME, Context.MODE_PRIVATE)
        return ingredientsSP
    }

    //Getting the unique SharePreferences for Livestock -*Fave
    private fun getLiveStockPrefs(): SharedPreferences{
        liveStockSp = context.getSharedPreferences(LIVESTOCK_PREFS_NAME, Context.MODE_PRIVATE)
        return liveStockSp
    }

    fun writeSp(){
        val editor : SharedPreferences.Editor = pref.edit()
        editor.putString(context.getString(R.string.pref_key), "NEXT")
        editor.apply()
    }

    fun checkPref() : Boolean{
        return !pref.getString(context.getString(R.string.pref_key),"null").equals("null")
    }

    fun clearPreference(){
        pref.edit().clear().apply()
        context.startActivity(Intent(context, AppEntryPoint::class.java))
        (context as BaseActivity).finish()
    }

    //Writing the map values to its specified shared preferences -*Fave
    fun writeMapValuesToPrefs(mapValuesAndKeys: Map<String ,Any>){
        val editor : SharedPreferences.Editor = getIngredientSp().edit()

        val gson = Gson()
        val json = gson.toJson(mapValuesAndKeys) // myObject - instance of MyObject
        editor.putString(INGREDIENTS, json)
        editor.commit()
    }
    fun writeSelectedValuesToPrefs(mapValuesAndKeys: Map<String ,Any>){
        val editor : SharedPreferences.Editor = getIngredientSp().edit()

        val gson = Gson()
        val json = gson.toJson(mapValuesAndKeys) // myObject - instance of MyObject
        editor.putString(SELECTED_INGREDIENTS, json)
        editor.commit()
    }

    //Getting all the values for the Ingredient SharedPreferences -*Fave
    fun getIngredientsValuesFromSP(): Map<String, Any>{
        val gson = Gson()
        val json = getIngredientSp().getString(INGREDIENTS, "")
        val type = object : TypeToken<Map<String, Any>>() {}.type
        return gson.fromJson(json, type)
    }
    fun getSelectedIngredientsValuesFromSP(): Map<String, Any>{
        val gson = Gson()
        val json = getIngredientSp().getString(SELECTED_INGREDIENTS, "")
        val type = object : TypeToken<Map<String, Any>>() {}.type
        return gson.fromJson(json, type)
    }

    //Writing the selected livestock to its specified SP -*Fave
    fun writeSelectedLiveStockToSP(selectedLiveStock: String){
        val editor = getLiveStockPrefs().edit()
        editor.putString(LIVESTOCK_KEY, selectedLiveStock)
        editor.apply()

    }

    //Getting the selected livestock -*Fave
    fun getSelectedLiveStockToSP(): String?{
        return getLiveStockPrefs().getString(LIVESTOCK_KEY, "")
    }
}

fun RecyclerView.init(context: Context){
    this.setHasFixedSize(true)
    this.layoutManager = LinearLayoutManager(context)
}