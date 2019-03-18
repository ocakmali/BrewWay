package com.ocakmali.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import com.ocakmali.data.db.CoffeeMakerConstants.COLUMN_NAME
import com.ocakmali.data.db.CoffeeMakerConstants.FTS_TABLE_NAME

@Fts4(contentEntity = CoffeeMakerEntity::class)
@Entity(tableName = FTS_TABLE_NAME)
data class CoffeeMakerFtsEntity(@ColumnInfo(name = COLUMN_NAME) val name: String)