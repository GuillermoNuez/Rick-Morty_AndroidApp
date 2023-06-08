package com.everis.rickmorty.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.everis.rickmorty.data.dao.CharacterDao
import com.everis.rickmorty.domain.usecase.entity.ArrayListConverter
import com.everis.rickmorty.domain.usecase.entity.CharacterEntity
import com.everis.rickmorty.domain.usecase.entity.LocationConverter
import com.everis.rickmorty.domain.usecase.entity.OriginConverter

@Database(entities = [CharacterEntity::class], version = 1)
@TypeConverters(OriginConverter::class, LocationConverter::class, ArrayListConverter::class)
abstract class CharacterDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}