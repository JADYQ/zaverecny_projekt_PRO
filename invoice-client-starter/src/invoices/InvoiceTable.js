/*  _____ _______         _                      _
 * |_   _|__   __|       | |                    | |
 *   | |    | |_ __   ___| |___      _____  _ __| | __  ___ ____
 *   | |    | | '_ \ / _ \ __\ \ /\ / / _ \| '__| |/ / / __|_  /
 *  _| |_   | | | | |  __/ |_ \ V  V / (_) | |  |   < | (__ / /
 * |_____|  |_|_| |_|\___|\__| \_/\_/ \___/|_|  |_|\_(_)___/___|
 *                                _
 *              ___ ___ ___ _____|_|_ _ _____
 *             | . |  _| -_|     | | | |     |  LICENCE
 *             |  _|_| |___|_|_|_|_|___|_|_|_|
 *             |_|
 *
 *   PROGRAMOVÁNÍ  <>  DESIGN  <>  PRÁCE/PODNIKÁNÍ  <>  HW A SW
 *
 * Tento zdrojový kód je součástí výukových seriálů na
 * IT sociální síti WWW.ITNETWORK.CZ
 *
 * Kód spadá pod licenci prémiového obsahu a vznikl díky podpoře
 * našich členů. Je určen pouze pro osobní užití a nesmí být šířen.
 * Více informací na http://www.itnetwork.cz/licence
 */

import React from "react";
import { Link } from "react-router-dom";
import { useState } from "react";
import { useEffect } from "react";
import { apiGet, apiPost, apiPut } from "../utils/api";





const InvoiceTable = ({ label, items, deleteInvoice }) => {


    const [statistics, setStatistics] = useState({});

    useEffect(() => {
        apiGet("/api/invoices/statistics").then((data) => setStatistics(data));
    }, []);

    return (
        <div>
            <hr />

            <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                <div>Celkový počet faktur: {statistics.invoicesCount}</div>
                <div>Celková cena faktur tento rok: {statistics.currentYearSum},- CZK</div>
                <div>Celková cena faktur: {statistics.allTimeSum},- CZK</div>
            </div>
            <hr />


            <table className="table table-striped table-hover">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Číslo faktury</th>
                        <th>Produkt</th>
                        <th>Cena</th>
                        <th>Datum plnění</th>
                        <th>Kupující</th>
                        <th>Prodávající</th>
                        <th className="tdaction">Akce</th>
                    </tr>
                </thead>
                <tbody>
                    {items.map((item, index) => (
                        <tr key={index + 1}>
                            <td>{index + 1}</td>
                            <td>{item.invoiceNumber}</td>
                            <td>{item.product}</td>
                            <td className="tdprice">{item.price},- CZK</td>
                            <td>{item.issued}</td>
                            <td>{item.buyer.name}</td>
                            <td>{item.seller.name}</td>

                            <td>
                                <div className="btn-group">
                                    <Link
                                        to={"/invoices/show/" + item._id}
                                        className="btn btn-sm btn-info"
                                    >
                                        Zobrazit
                                    </Link>
                                    <Link
                                        to={"/invoices/edit/" + item._id}
                                        className="btn btn-sm btn-warning"
                                    >
                                        Upravit
                                    </Link>
                                    <button
                                        onClick={() => deleteInvoice(item._id)}
                                        className="btn btn-sm btn-danger"
                                    >
                                        Odstranit
                                    </button>
                                </div>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
            <Link to={"/invoices/create"} className="btn btn-success">
                Nová faktura
            </Link>
        </div>
    );
};

export default InvoiceTable;
