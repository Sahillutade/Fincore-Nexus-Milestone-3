import { useEffect, useState } from "react";
import MainLayout from "../../layouts/MainLayout";
import { getAllCustomers } from "../../services/customerService";
import "./Customers.css";

function Customers() {
    const [customers, setCustomers] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        loadCustomers();
    }, []);

    async function loadCustomers() {
        try {
            const data = await getAllCustomers();
            setCustomers(data);
        } catch (error) {
            console.error("Error loading customers:", error);
        } finally {
            setLoading(false);
        }
    }

    return (
        <MainLayout>
            <div className="customers-page">

                <h1>Customers</h1>

                {loading ? (
                    <h3>Loading...</h3>
                ) : (
                    <table className="customers-table">

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
                )}

            </div>
        </MainLayout>
    );
}

export default Customers;