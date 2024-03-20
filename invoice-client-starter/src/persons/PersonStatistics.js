import { useEffect } from "react";
import { apiGet } from "../utils/api";
import { useState } from "react";

function PersonStatistics() {

    const[personStatistics,setPersonStatistics] = useState([]);

    useEffect(() => {apiGet("/api/persons/statistics").then((data) => setPersonStatistics(data));}, []);

    return (<>

        <h1>Statistiky</h1>

        <table className="table table-striped table-hover">
            <thead>
                <tr>
                    <th className="tdnumber">#</th>
                    <th className="tdstatisticsname">Jméno</th>
                    <th className="tdstatisticsvalue" >Hodnota vystavených faktur</th>
                    
                </tr>
                
            </thead>
            <tbody>
                {personStatistics.map((personStatistics, index) => (
                    <tr key={index + 1}>
                        <td>{index + 1}</td>
                        <td>{personStatistics.personName}</td>
                        <td>{personStatistics.revenue},- CZK</td>
                        
                </tr>))}</tbody>
        </table>
    </>)
}

export default PersonStatistics;