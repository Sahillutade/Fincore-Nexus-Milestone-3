import { useEffect, useState } from "react";
import { getAllCustomers } from "../../services/customerService";

function CustomerTable() {

    const [customers, setCustomers] = useState([]);

    useEffect(() => {
        loadCustomers();
    }, []);

    async function loadCustomers() {

        try {

            const data = await getAllCustomers();

            setCustomers(data.slice(-5).reverse());

        } catch (error) {

            console.error("Error loading customers:", error);

        }

    }

    return (

        <table>

            <thead>

                <tr>

                    <th>Customer ID</th>
                    <th>Name</th>
                    <th>Address</th>
                    <th>KYC Status</th>

                </tr>

            </thead>

            <tbody>

                {customers.map((customer) => (

                    <tr key={customer.custid}>

                        <td>{customer.custid.substring(0, 8)}...</td>

                        <td>{customer.name}</td>

                        <td>{customer.address}</td>

                        <td>{customer.kycStatus}</td>

                    </tr>

                ))}

            </tbody>

        </table>

    );

}

export default CustomerTable;