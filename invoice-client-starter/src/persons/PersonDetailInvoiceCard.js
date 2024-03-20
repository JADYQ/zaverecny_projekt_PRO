import { Link } from "react-router-dom";
export function InvoiceCard({ invoice }) {
    return (<div className="card">
        <div className="card-body">
            <h4 className="card-title">{invoice.product}</h4>
            <div><b>Číslo faktury:</b>  {invoice.invoiceNumber}</div>
            <div><b>Cena produktu: </b>  {invoice.price},- CZK</div>
            <div><b>Datum splatnosti:</b>  {invoice.dueDate}</div>
            <div><b>Poznámka: </b>  {invoice.note}</div>

        </div>
        <div className="btn-group">
            <Link
                to={"/invoices/show/" + invoice._id}
                className="btn btn-sm btn-info"
            >Zobrazit
            </Link>
            <Link
                to={"/invoices/edit/" + invoice._id}
                className="btn btn-sm btn-warning"
            >Upravit
            </Link>
        </div>
    </div>);
}