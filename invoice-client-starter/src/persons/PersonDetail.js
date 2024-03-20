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

import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import "./PersonDetail.css"
import { InvoiceCard } from "./PersonDetailInvoiceCard";
import { apiGet } from "../utils/api";
import Country from "./Country";
2
const PersonDetail = () => {
    const { id } = useParams();
    const [person, setPerson] = useState({});
    const [receivedInvoices, setReceivedInvoices] = useState([]);
    const [issuedInvoices, setIssuedInvoices] = useState([]);
    let lableIssued = "Odeslané faktury:"; // Used, when there are no issued invoices to hide the label 
    let lableReceived = "Přijaté faktury:"; // Used, when there are no issued invoices to hide the label 
    
    if(issuedInvoices.length === 0){
        lableIssued = "";
    }
    if(receivedInvoices.length === 0){
        lableReceived = "";
    }
    

    useEffect(() => {
        apiGet("/api/persons/" + id).then((data) => setPerson(data));
    }, [id]);
    const country = Country.CZECHIA === person.country ? "Česká republika" : "Slovensko";

    useEffect(() => {
        apiGet("/api/identification/" + person.identificationNumber + "/sales").then((data) => setReceivedInvoices(data));
    }, [person.identificationNumber]);

    useEffect(() => {
        apiGet("/api/identification/" + person.identificationNumber + "/purchases").then((data) => setIssuedInvoices(data));
    }, [person.identificationNumber]);


    return (
        <div className="split-container">
            <div className="personDetail">
                <h1>Detail osoby</h1>
                <br />
                <h3>{person.name}</h3>
                <p>
                    <strong>IČO:</strong>
                    <br />
                    {person.identificationNumber}
                </p>
                
                <p>
                    <strong>DIČ:</strong>
                    <br />
                    {person.taxNumber}
                </p>
                <p>
                    <strong>Bankovní účet:</strong>
                    <br />
                    {person.accountNumber}/{person.bankCode} ({person.iban})
                </p>
                <p>
                    <strong>Tel.:</strong>
                    <br />
                    {person.telephone}
                </p>
                <p>
                    <strong>Mail:</strong>
                    <br />
                    {person.mail}
                </p>
                <p>
                    <strong>Sídlo:</strong>
                    <br />
                    {person.street}, {person.city},
                    {person.zip}, {country}
                </p>
                <p>
                    <strong>Poznámka:</strong>
                    <br />
                    {person.note}
                </p>

            </div>
            <div className="invoiceCardList">
                <h3>{lableIssued}</h3>
                {issuedInvoices.map(invoice => (
                    <InvoiceCard key={invoice._id} invoice={invoice} />
                ))}
            </div>
            <div className="invoiceCardList">
                <h3>{lableReceived}</h3>
                {receivedInvoices.map(invoice => (
                    <InvoiceCard key={invoice._id} invoice={invoice} className="card"/>
                ))}
            </div>
        </div>
    );
};

export default PersonDetail;
