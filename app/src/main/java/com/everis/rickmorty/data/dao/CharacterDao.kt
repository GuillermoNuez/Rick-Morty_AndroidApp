package com.everis.rickmorty.data.dao

import androidx.room.*
import com.everis.rickmorty.domain.usecase.entity.CharacterEntity

@Dao
interface CharacterDao {
    @Insert
    suspend fun insert(character: CharacterEntity)

    @Insert
    suspend fun insertAll(characters: List<CharacterEntity>)

    @Update
    suspend fun update(character: CharacterEntity)

    @Delete
    suspend fun delete(character: CharacterEntity)

    @Query("SELECT * FROM characters")
    suspend fun getAll(): List<CharacterEntity>

    @Query("SELECT * FROM characters WHERE roomId = :id")
    suspend fun getById(id: Int): CharacterEntity?

    @Query("SELECT * FROM characters WHERE name = :name")
    suspend fun getByName(name: String): CharacterEntity?

    @Query("DELETE FROM characters")
    suspend fun deleteAll()
}