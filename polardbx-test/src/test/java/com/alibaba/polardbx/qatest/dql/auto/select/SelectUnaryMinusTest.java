/*
 * Copyright [2013-2021], Alibaba Group Holding Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.polardbx.qatest.dql.auto.select;

import com.alibaba.polardbx.qatest.AutoReadBaseTestCase;
import com.alibaba.polardbx.qatest.data.ColumnDataGenerator;
import com.alibaba.polardbx.qatest.data.ExecuteTableSelect;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.alibaba.polardbx.qatest.validator.DataValidator.selectContentSameAssert;

/**
 * 负值查询
 *
 * @author zhuoxue.yll
 * @since 5.0.1
 * 2016年11月2日
 */

public class SelectUnaryMinusTest extends AutoReadBaseTestCase {
    ColumnDataGenerator columnDataGenerator = new ColumnDataGenerator();

    @Parameters(name = "{index}:table={0}")
    public static List<String[]> prepareData() {
        return Arrays.asList(ExecuteTableSelect.selectBaseOneTable());
    }

    public SelectUnaryMinusTest(String baseOneTableName) {
        this.baseOneTableName = baseOneTableName;
    }

    /**
     * @since 5.0.1
     */
    @Test
    public void cloumMinusTest() throws Exception {
        String sql = String.format("select -integer_test as a from %s where varchar_test=?", baseOneTableName);
        List<Object> param = new ArrayList<Object>();
        param.add(columnDataGenerator.varchar_testValue);
        selectContentSameAssert(sql, param, mysqlConnection, tddlConnection);

        sql = String.format("select pk-integer_test as a from %s where varchar_test=?", baseOneTableName);
        selectContentSameAssert(sql, param, mysqlConnection, tddlConnection);

        sql = String.format("select pk-(-integer_test) as a from %s where varchar_test=?", baseOneTableName);
        selectContentSameAssert(sql, param, mysqlConnection, tddlConnection);
    }

    /**
     * @since 5.0.1
     */
    @Test
    public void conditionMinusTest() throws Exception {
        String sql = String.format("select * from %s where integer_test<pk-?", baseOneTableName);
        List<Object> param = new ArrayList<Object>();
        param.add(50);
        selectContentSameAssert(sql, param, mysqlConnection, tddlConnection);
    }
}