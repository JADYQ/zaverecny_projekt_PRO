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
import {Link} from "react-router-dom";
import "../style.css";

const PersonTable = ({label, items, deletePerson}) => {
    
    return (
        <div>

            <p>
                {label} {items.length}
            </p>
            <hr />


            <table className="table table-striped table-hover">
                <thead>
                <tr>
                    <th className="tdnumber">#</th>
                    <th>Jméno</th>
                    <th>Telefoní číslo</th>
                    <th>E-mail</th>
                    <th className="tdaction">Akce</th>
                </tr>
                </thead>
                <tbody>
                {items.map((item, index) => (
                    <tr key={index + 1}>
                        <td className="tdnumber">{index + 1}</td>

                        <td>{item.name}</td>
                        <td>{item.telephone}</td>
                        <td>{item.mail}</td>
                        <td className="tdaction">
                            <div className="btn-group">
                                <Link
                                    to={"/persons/show/" + item._id}
                                    className="btn btn-info"
                                >
                                    Zobrazit
                                </Link>
                                <Link
                                    to={"/persons/edit/" + item._id}
                                    className="btn btn-warning"
                                >
                                    Upravit
                                </Link>
                                <button
                                    onClick={() => deletePerson(item._id)}
                                    className="btn btn-danger"
                                >
                                    Odstranit
                                </button>   
                            </div>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
            <Link to={"/persons/create"} className="btn btn-success">
                Nová osoba
            </Link>
            </div>
    );
};

export default PersonTable;
