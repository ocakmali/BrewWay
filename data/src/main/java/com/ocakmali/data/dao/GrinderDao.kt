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

package com.ocakmali.data.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import com.ocakmali.data.BaseDao
import com.ocakmali.data.db.DbConstants.FTS_DOC_ID
import com.ocakmali.data.db.GrinderConstants.COLUMN_ID
import com.ocakmali.data.db.GrinderConstants.FTS_TABLE_NAME
import com.ocakmali.data.db.GrinderConstants.TABLE_NAME
import com.ocakmali.data.entity.GrinderEntity

@Dao
abstract class GrinderDao : BaseDao<GrinderEntity> {

    @Query("SELECT * FROM $TABLE_NAME")
    abstract fun loadGrinders(): DataSource.Factory<Int, GrinderEntity>

    @Query("""SELECT * FROM $TABLE_NAME
        JOIN $FTS_TABLE_NAME ON $COLUMN_ID = $FTS_DOC_ID
            WHERE $FTS_TABLE_NAME MATCH :query LIMIT :limit
            """)
    abstract fun search(query: String, limit: Int): List<GrinderEntity>

    @Query("""SELECT * FROM $TABLE_NAME
        JOIN $FTS_TABLE_NAME ON $COLUMN_ID = $FTS_DOC_ID
            WHERE $FTS_TABLE_NAME MATCH :name LIMIT 1
            """)
    abstract fun findByName(name: String): GrinderEntity?
}