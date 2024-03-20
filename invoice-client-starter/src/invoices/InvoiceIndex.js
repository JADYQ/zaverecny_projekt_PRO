import React, { useEffect, useState } from "react";
import { apiDelete, apiGet } from "../utils/api";
import InvoiceTable from "./InvoiceTable";
import InvoiceFilter from "./InvoiceFilter";



const InvoiceIndex = () => {
    const [invoices, setInvoices] = useState([]);

    //Filtrace - stavy
    const [personList, setPersonList] = useState([]);
    const [filterState, setFilterState] = useState({
        buyerID: undefined,
        sellerID: undefined,
        product: undefined,
        minPrice: undefined,
        maxPrice: undefined,
        limit: undefined




    });
    //Filtrace - useEffect
    useEffect(() => {
        apiGet("/api/persons").then((data) => setPersonList(data))
    }, []);

    // - handle change 
    const handleChange = (e) => {
        // pokud vybereme prázdnou hodnotu (máme definováno jako true/false/'' v komponentách), nastavíme na undefined
        if (e.target.value === "false" || e.target.value === "true" || e.target.value === '') {
            setFilterState(prevState => {
                return { ...prevState, [e.target.name]: undefined }
            });
        } else {
            setFilterState(prevState => {
                return { ...prevState, [e.target.name]: e.target.value }
            });
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const params = filterState;

        const data = await apiGet("/api/invoices", params);
        setInvoices(data);
    };

    const deleteInvoice = async (id) => {
        try {
            await apiDelete("/api/invoices/" + id);
        } catch (error) {
            console.log(error.message);
            alert(error.message)
        }
        setInvoices(invoices.filter((item) => item._id !== id));
    };

    useEffect(() => {
        apiGet("/api/invoices").then((data) => setInvoices(data));
    }, []);

    return (
        <div>
            <h1>Seznam faktur</h1>


            <InvoiceFilter
                handleChange={handleChange}
                handleSubmit={handleSubmit}
                filter={filterState}
                personList={personList}
                confirm="Filtrovat faktury" />  

            <InvoiceTable
                deleteInvoice={deleteInvoice}
                items={invoices}
                label="Počet faktur:"
            />
        </div>

    );
};
export default InvoiceIndex;
