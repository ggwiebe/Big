/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gridgain.big.test;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.configuration.IgniteConfiguration;

import com.gridgain.big.model.Customer;
import com.gridgain.big.model.Contact;
import com.gridgain.big.utility.ParseTypes;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Properties;

/**
 * Ignite Client to interact with SQL API
 */
public class QueryCustomerContacts {

    /**
     * Start an Ignite Client 
     *
     * @param args Command line arguments, none required.
     * @throws IgniteException If failed.
     */
    public static void main(String[] args) throws IgniteException, IOException {
        int n = 0; // record counter

        try (Ignite ignite = Ignition.start("big-client.xml")){
            System.out.println(">>> Query Customer & Contact with SQL:");

            /*
             * ------------------------------------------------------------------------------------------------------------
             * Query Customer
             * ------------------------------------------------------------------------------------------------------------
             */
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Query Customer Table...");

            try (IgniteCache<Integer, Customer> custCache = ignite.cache("CustomerCache")){

                // SQL clause which selects salaries based on range.
                // Extract fields of the entry.
                String sql = "select * from Person where salary > ? and salary <= ?";
                String query = "SELECT * " + 
                               "   FROM BIG.CONTACT  " + 
                               " WHERE SEQUENCENUM = 3336 " +
                               "   AND UPDATEDT  BETWEEN '2020-10-16 06:02:30.050' AND '2020-10-16 06:02:31.052' " +
                               "   AND FULLNAME = 'Colleen Mcpherson' " +
                               "   AND STREET = '4 Main St' " +
                               "   AND CITY = 'Berkeley' " +
                               "   AND STATE = 'CA' " +
                               "   AND ZIP = '94704' " +
                               "   AND PHONENUM = '(510) xxx-xxxx' ";

                print(
                    "Contacts of a specific record (queried with SQL query): ",
                    //custCache.query(new SqlFieldsQuery(query).setArgs(0, 1000)).getAll()
                    custCache.query(new SqlFieldsQuery(query)).getAll()
                );
            {
            }
            } catch (Exception e ) {
                System.out.println("Caught Exception - Running Customer Query: " + e);
            } finally {
                System.out.println(">>> Customer query count: " + n);
            }

            /*
             * ------------------------------------------------------------------------------------------------------------
             * Contact
             * ------------------------------------------------------------------------------------------------------------
             */
            // System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>> ContactCache...");
            // reader = Files.newBufferedReader(Paths.get(dataLocation + "contact.csv"), StandardCharsets.UTF_8);
            // csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withEscape('\\').withQuoteMode(QuoteMode.NONE).withFirstRecordAsHeader().withTrim());

            // try (IgniteDataStreamer<Integer, Contact> streamer = ignite.dataStreamer("ContactCache")){
            // 	n = 0; // record counter
            //     for (CSVRecord csvRecord : csvParser) {
            //     	Integer k = ParseTypes.parseInteger(csvRecord.get(0));
            //         Contact v = null;
            //         try {
            //             v = new Contact(
            //                 ParseTypes.parseTimestamp(csvRecord.get(1)),
            //                 ParseTypes.parseString(csvRecord.get(2)),
            //                 ParseTypes.parseString(csvRecord.get(3)),
            //                 ParseTypes.parseString(csvRecord.get(4)),
            //                 ParseTypes.parseString(csvRecord.get(5)),
            //                 ParseTypes.parseString(csvRecord.get(6)),
            //                 ParseTypes.parseString(csvRecord.get(7)),
            //                 ParseTypes.parseString(csvRecord.get(8))
            //             );
            //         } catch (NumberFormatException e) {
            //             // TODO Auto-generated catch block
            //             e.printStackTrace();
            //         }
            //         streamer.addData(k, v);
            //     	n += 1;
            //     }
            // } catch (Exception e ) {
            //     System.out.println("Caught Exception - loading Contact: " + e);
            // } finally {
            //     System.out.println(">>> Load Contact count: " + n);
            // }

        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
        }
        
        System.out.println(">>> Query Customer & Contact with SQL - COMPLETE!!!");
    }

    /**
     * Prints message and query results.
     *
     * @param msg Message to print before all objects are printed.
     * @param col Query results.
     */
    private static void print(String msg, Iterable<?> col) {
        print(msg);
        print(col);
    }

    /**
     * Prints message.
     *
     * @param msg Message to print before all objects are printed.
     */
    private static void print(String msg) {
        System.out.println();
        System.out.println(">>> " + msg);
    }

    /**
     * Prints query results.
     *
     * @param col Query results.
     */
    private static void print(Iterable<?> col) {
        for (Object next : col)
            System.out.println(">>>     " + next);
    }

}
