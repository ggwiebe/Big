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

package com.gridgain.big.load;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;

import com.gridgain.big.model.Customer;
import com.gridgain.big.model.Contact;
import com.gridgain.big.utility.ParseTypes;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;

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
 * Ignite Client loads data from CSV files to Caches via Ignite DataStreamer
 */
public class LoadCachesFromCsv {

    private static final Properties props = new Properties();

    /**
     * Start an Ignite Client and perform CSV Reading -> Cache Writing (via Ignite DataStreamer)
     *
     * @param args Command line arguments, none required.
     * @throws IgniteException If failed.
     */
    public static void main(String[] args) throws IgniteException, IOException {
        String dataLocation = "/data/big/"; // default location
        DecimalFormat numFormat = (DecimalFormat)NumberFormat.getCurrencyInstance();
        String symbol = numFormat.getCurrency().getSymbol();
        numFormat.setNegativePrefix("-"+symbol);
        numFormat.setNegativeSuffix("");
        CSVParser csvParser = null;
        int n = 0; // record counter

        try (InputStream in = IgniteConfiguration.class.getClassLoader().getResourceAsStream("big.properties")) {
            props.load(in);
            dataLocation = props.getProperty("dataLocation");
            System.out.println(">>>>>>>>>>>>>>>>> loaded properties big.properties; dataLocation set to: " + dataLocation);
        }
        catch (Exception ignored) {
            System.out.println(">>>>>>>>>>>>>>>>> Failed loading properties; using default dataLocation: " + dataLocation);
        }

        try (Ignite ignite = Ignition.start("big-client.xml")){
            System.out.println(">>> CSV Stream Loading caches:");

            /*
             * ------------------------------------------------------------------------------------------------------------
             * Customer
             * ------------------------------------------------------------------------------------------------------------
             */
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>> CustomerCache...");
            Reader reader = Files.newBufferedReader(Paths.get(dataLocation + "customer.csv"), StandardCharsets.UTF_8);
            csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withEscape('\\').withQuoteMode(QuoteMode.NONE).withFirstRecordAsHeader().withTrim());

            try (IgniteDataStreamer<Integer, Customer> streamer = ignite.dataStreamer("CustomerCache")){
            	n = 0; // record counter
                for (CSVRecord csvRecord : csvParser) {
                	Integer k = ParseTypes.parseInteger(csvRecord.get(0));
                    Customer v = null;
                    try {
                        v = new Customer(
                            ParseTypes.parseString(csvRecord.get(1)),
                            ParseTypes.parseTimestamp(csvRecord.get(2)),
                            ParseTypes.parseString(csvRecord.get(3)),
                            ParseTypes.parseString(csvRecord.get(4)),
                            ParseTypes.parseString(csvRecord.get(5)),
                            ParseTypes.parseString(csvRecord.get(6))
                        );
                    } catch (NumberFormatException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    streamer.addData(k, v);
                	n += 1;
                }
            } catch (Exception e ) {
                System.out.println("Caught Exception - loading Customer: " + e);
            } finally {
                System.out.println(">>> Load Customer count: " + n);
            }

            /*
             * ------------------------------------------------------------------------------------------------------------
             * Contact
             * ------------------------------------------------------------------------------------------------------------
             */
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>> ContactCache...");
            reader = Files.newBufferedReader(Paths.get(dataLocation + "contact.csv"), StandardCharsets.UTF_8);
            csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withEscape('\\').withQuoteMode(QuoteMode.NONE).withFirstRecordAsHeader().withTrim());

            try (IgniteDataStreamer<Integer, Contact> streamer = ignite.dataStreamer("ContactCache")){
            	n = 0; // record counter
                for (CSVRecord csvRecord : csvParser) {
                	Integer k = ParseTypes.parseInteger(csvRecord.get(0));
                    Contact v = null;
                    try {
                        v = new Contact(
                            ParseTypes.parseTimestamp(csvRecord.get(1)),
                            ParseTypes.parseString(csvRecord.get(2)),
                            ParseTypes.parseString(csvRecord.get(3)),
                            ParseTypes.parseString(csvRecord.get(4)),
                            ParseTypes.parseString(csvRecord.get(5)),
                            ParseTypes.parseString(csvRecord.get(6)),
                            ParseTypes.parseString(csvRecord.get(7)),
                            ParseTypes.parseString(csvRecord.get(8))
                        );
                    } catch (NumberFormatException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    streamer.addData(k, v);
                	n += 1;
                }
            } catch (Exception e ) {
                System.out.println("Caught Exception - loading Contact: " + e);
            } finally {
                System.out.println(">>> Load Contact count: " + n);
            }

        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            csvParser.close();
        }
        
        System.out.println(">>> CSV Stream Loading caches - COMPLETE!!!");
    }
}
