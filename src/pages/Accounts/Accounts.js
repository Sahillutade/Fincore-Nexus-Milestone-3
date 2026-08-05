import { useEffect, useState } from "react";
import MainLayout from "../../layouts/MainLayout";
import { getAllAccounts } from "../../services/accountService";
import "./Accounts.css";

function Accounts() {

    const [accounts, setAccounts] = useState([]);

    useEffect(() => {
        loadAccounts();
    }, []);

    async function loadAccounts() {

        try {

            const data = await getAllAccounts();
            setAccounts(data);

        } catch (error) {

            console.error(error);

        }

    }

    return (

        <MainLayout>

            <div className="accounts-page">

                <div className="page-header">
                    <h2>Accounts</h2>
                </div>

                <table className="accounts-table">

                    <thead>

                        <tr>
                            <th>ID</th>
                            <th>Account Number</th>
                            <th>Holder Name</th>
                            <th>Type</th>
                            <th>Balance</th>
                            <th>Branch</th>
                            <th>IFSC</th>
                            <th>Status</th>
                        </tr>

                    </thead>

                    <tbody>

                        {accounts.length > 0 ? (

                            accounts.map((account) => (

                                <tr key={account.accountId}>

                                    <td>{account.accountId}</td>
                                    <td>{account.accountNumber}</td>
                                    <td>{account.accountHolderName}</td>
                                    <td>{account.accountType}</td>
                                    <td>₹ {account.balance}</td>
                                    <td>{account.branch}</td>
                                    <td>{account.ifscCode}</td>
                                    <td>{account.status}</td>

                                </tr>

                            ))

                        ) : (

                            <tr>

                                <td colSpan="8" style={{ textAlign: "center" }}>
                                    No Accounts Found
                                </td>

                            </tr>

                        )}

                    </tbody>

                </table>

            </div>

        </MainLayout>

    );

}

export default Accounts;