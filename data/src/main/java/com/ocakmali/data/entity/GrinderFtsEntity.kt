package com.ocakmali.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import com.ocakmali.data.db.GrinderConstants.COLUMN_NAME
import com.ocakmali.data.db.GrinderConstants.FTS_TABLE_NAME

@Fts4(contentEntity = GrinderEntity::class)
@Entity(tableName = FTS_TABLE_NAME)
data class GrinderFtsEntity(@ColumnInfo(name = COLUMN_NAME) val name: String)