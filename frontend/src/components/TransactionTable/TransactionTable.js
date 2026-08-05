import { useEffect, useState } from "react";
import { getAllTransactions } from "../../services/transactionService";

function TransactionTable() {

    const [transactions, setTransactions] = useState([]);

    useEffect(() => {
        loadTransactions();
    }, []);

    async function loadTransactions() {
        const data = await getAllTransactions();
        setTransactions(data);
    }

    return (

        <table>

            <thead>

                <tr>

                    <th>Reference</th>
                    <th>Type</th>
                    <th>Amount</th>
                    <th>Status</th>

                </tr>

            </thead>

            <tbody>

                {transactions.slice(0,5).map(transaction => (

                    <tr key={transaction.id}>

                        <td>{transaction.transactionReference}</td>

                        <td>{transaction.transactionType}</td>

                        <td>₹{Number(transaction.amount).toLocaleString()}</td>

                        <td>

                            <span
                                className={
                                    transaction.status==="SUCCESS"
                                    ?"status-success"
                                    :"status-pending"
                                }
                            >

                                {transaction.status}

                            </span>

                        </td>

                    </tr>

                ))}

            </tbody>

        </table>

    );

}

export default TransactionTable;