package com.everis.rickmorty.domain.usecase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.everis.rickmorty.ui.main.Location
import com.everis.rickmorty.ui.main.Origin
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true) val roomId: Int = 0,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("species") var species: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("gender") var gender: String? = null,
    @SerializedName("origin") var origin: Origin? = null,
    @SerializedName("location") var location: Location? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("episode") var episode: ArrayList<String> = arrayListOf(),
    @SerializedName("url") var url: String? = null,
    @SerializedName("created") var created: String? = null
)

class OriginConverter {
    @TypeConverter
    fun fromOrigin(origin: Origin?): String? {
        return Gson().toJson(origin)
    }

    @TypeConverter
    fun toOrigin(json: String?): Origin? {
        return Gson().fromJson(json, Origin::class.java)
    }
}


class LocationConverter {
    @TypeConverter
    fun fromLocation(location: Location?): String? {
        return Gson().toJson(location)
    }

    @TypeConverter
    fun toLocation(json: String?): Location? {
        return Gson().fromJson(json, Location::class.java)
    }
}

class ArrayListConverter {
    @TypeConverter
    fun fromArrayList(value: ArrayList<String>): String {
        val gson = Gson()
        return gson.toJson(value)
    }

    @TypeConverter
    fun toArrayList(value: String): ArrayList<String> {
        val gson = Gson()
        val listType = object : TypeToken<ArrayList<String>>() {}.type
        return gson.fromJson(value, listType)
    }
}