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
import "bootstrap/dist/css/bootstrap.min.css";
import {
  BrowserRouter as Router,
  Link,
  Route,
  Routes,
  Navigate,
} from "react-router-dom";

import PersonIndex from "./persons/PersonIndex";
import PersonDetail from "./persons/PersonDetail";
import PersonForm from "./persons/PersonForm";
import InvoiceIndex from "./invoices/InvoiceIndex";
import InvoiceForm from "./invoices/InvoiceForm";
import InvoiceDetail from "./invoices/InvoiceDetail";
import PersonStatistics from "./persons/PersonStatistics";
import invoiceIcon from './invoiceIcon.png';

export function App() {
  return (
    <Router>
      <div className="container">
        <nav className="navbar navbar-expand-lg navbar-light bg-light">
    <Link to="https://i.kym-cdn.com/entries/icons/original/000/002/144/You_Shall_Not_Pass!_0-1_screenshot.jpg"><img src={invoiceIcon} width="45" height="45" alt="Logo"/></Link>
          <ul className="navbar-nav mr-auto">
            <li className="nav-item">
              <Link to={"/persons"} className="nav-link">
                Osoby
              </Link>
            </li>
            <li>
              <Link to={"/invoices"} className="nav-link">
                Faktury
              </Link>
            </li>
            <li>
              <Link to={"/persons/statistics"} className="nav-link">
                Statistika
              </Link>
            </li>
          </ul>
        
        </nav>

        <Routes>
          <Route index element={<Navigate to={"/persons"} />} />
          <Route path="/persons">
            <Route index element={<PersonIndex />} />
            <Route path="show/:id" element={<PersonDetail />} />
            <Route path="create" element={<PersonForm />} />
            <Route path="edit/:id" element={<PersonForm />} />
            <Route path="statistics" element={<PersonStatistics/>} />
          </Route>
          <Route path="/invoices">
            <Route index element={<InvoiceIndex />} />
            <Route path="create" element={<InvoiceForm/>} />
            <Route path="show/:id" element={<InvoiceDetail />} />
            <Route path="edit/:id" element={<InvoiceForm />} />



          </Route>
        </Routes>
      </div>
    </Router>
  );
}

export default App;
