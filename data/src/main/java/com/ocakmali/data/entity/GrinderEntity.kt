/*
 * MIT License
 *
 * Copyright (c) 2019 Mehmet Ali Ocak
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.ocakmali.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ocakmali.data.db.GrinderConstants.COLUMN_ID
import com.ocakmali.data.db.GrinderConstants.COLUMN_NAME
import com.ocakmali.data.db.GrinderConstants.TABLE_NAME
import com.ocakmali.domain.model.Grinder

internal fun GrinderEntity.toGrinder() = Grinder(name, id)

internal fun Grinder.toEntity() = GrinderEntity(name, id)

@Entity(tableName = TABLE_NAME)
data class GrinderEntity(@ColumnInfo(name = COLUMN_NAME) val name: String,
                         @PrimaryKey(autoGenerate = true)
                         @ColumnInfo(name = COLUMN_ID) val id: Int)