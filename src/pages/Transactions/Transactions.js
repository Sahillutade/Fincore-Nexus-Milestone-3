import { useEffect, useState } from "react";
import MainLayout from "../../layouts/MainLayout";

function Transactions() {
  const [transactions, setTransactions] = useState([]);
  const [filteredTransactions, setFilteredTransactions] = useState([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState("");

  useEffect(() => {
    loadTransactions();
  }, []);

  const loadTransactions = async () => {
    try {
      setLoading(true);

      const response = await fetch(
        "http://localhost:8080/transactions/all"
      );

      if (!response.ok) {
        throw new Error("Failed to fetch transactions");
      }

      const data = await response.json();

      setTransactions(data);
      setFilteredTransactions(data);
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = (value) => {
    setSearch(value);

    const filtered = transactions.filter((transaction) => {
      return (
        transaction.id.toString().includes(value) ||
        transaction.transactionType
          .toLowerCase()
          .includes(value.toLowerCase()) ||
        transaction.status
          .toLowerCase()
          .includes(value.toLowerCase()) ||
        transaction.remarks
          ?.toLowerCase()
          .includes(value.toLowerCase())
      );
    });

    setFilteredTransactions(filtered);
  };

  return (
    <MainLayout>
      <div className="container-fluid mt-4">

        <div className="d-flex justify-content-between align-items-center mb-4">
          <h2 className="fw-bold">Transactions</h2>

          <button
            className="btn btn-primary"
            onClick={loadTransactions}
          >
            🔄 Refresh
          </button>
        </div>

        <div className="row mb-3">
          <div className="col-md-4">
            <input
              type="text"
              className="form-control"
              placeholder="Search by ID, Type, Status..."
              value={search}
              onChange={(e) => handleSearch(e.target.value)}
            />
          </div>
        </div>

        {loading ? (
          <div className="text-center mt-5">
            <h5>Loading Transactions...</h5>
          </div>
        ) : filteredTransactions.length === 0 ? (
          <div className="alert alert-warning">
            No Transactions Found
          </div>
        ) : (
          <div className="table-responsive">

            <table className="table table-hover table-bordered align-middle">

              <thead className="table-dark">

                <tr>
                  <th>Transaction ID</th>
                  <th>Account ID</th>
                  <th>Customer ID</th>
                  <th>Type</th>
                  <th>Amount</th>
                  <th>Status</th>
                  <th>Balance After</th>
                  <th>Date & Time</th>
                  <th>Remarks</th>
                </tr>

              </thead>

              <tbody>

                {filteredTransactions.map((transaction) => (

                  <tr key={transaction.id}>

                    <td className="fw-bold text-primary">
                      TXN-{String(transaction.id).padStart(5, "0")}
                    </td>

                    <td>{transaction.accountId}</td>

                    <td>
                      {transaction.customerId.slice(0, 8)}...
                    </td>

                    <td>
                      <span
                        className={
                          transaction.transactionType === "DEPOSIT"
                            ? "badge bg-success"
                            : transaction.transactionType === "WITHDRAWAL"
                            ? "badge bg-danger"
                            : "badge bg-primary"
                        }
                      >
                        {transaction.transactionType}
                      </span>
                    </td>

                    <td className="fw-bold">
                      ₹{Number(transaction.amount).toLocaleString("en-IN")}
                    </td>

                    <td>
                      <span
                        className={
                          transaction.status === "SUCCESS"
                            ? "badge bg-success"
                            : transaction.status === "FAILED"
                            ? "badge bg-danger"
                            : "badge bg-warning text-dark"
                        }
                      >
                        {transaction.status}
                      </span>
                    </td>

                    <td>
                      ₹
                      {Number(
                        transaction.balanceAfterTransaction
                      ).toLocaleString("en-IN")}
                    </td>

                    <td>
                      {new Date(
                        transaction.transactionDate
                      ).toLocaleString()}
                    </td>

                    <td>{transaction.remarks}</td>

                  </tr>

                ))}

              </tbody>

            </table>

          </div>
        )}
      </div>
    </MainLayout>
  );
}

export default Transactions;