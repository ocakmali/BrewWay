package com.ocakmali.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import com.ocakmali.data.db.CoffeeConstants.COLUMN_NAME
import com.ocakmali.data.db.CoffeeConstants.FTS_TABLE_NAME

@Fts4(contentEntity = CoffeeEntity::class)
@Entity(tableName = FTS_TABLE_NAME)
data class CoffeeFtsEntity(@ColumnInfo(name = COLUMN_NAME) val name: String)