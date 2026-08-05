import { FaEye } from "react-icons/fa";
import "./KYCTable.css";


function KYCTable({
  records,
  search,
  setSearch,
  statusFilter,
  setStatusFilter,
  currentPage,
  totalPages,
  setCurrentPage,
  recordsPerPage,
  setSelectedKyc,
  handleVerify,
  handleReject,
  totalRecords,
}) {

  return (

    <div className="kyc-table-card">

      {/* Header */}

      <div className="kyc-table-header">

        <h2>KYC Records</h2>

        <div className="table-controls">

          <input
            type="text"
            className="search-input"
            placeholder="Search Customer, PAN..."
            value={search}
            onChange={(e) => setSearch(e.target.value)}
          />

          <select
            className="status-filter"
            value={statusFilter}
            onChange={(e) => setStatusFilter(e.target.value)}
          >

            <option value="ALL">All Status</option>

            <option value="PENDING">Pending</option>

            <option value="VERIFIED">Verified</option>

            <option value="REJECTED">Rejected</option>

          </select>

        </div>

      </div>

      {/* Table */}

      <div className="kyc-table-wrapper">

        <table className="kyc-table">

          <thead>

            <tr>

              <th>ID</th>

              <th>Customer ID</th>

              <th>PAN</th>

              <th>Aadhaar</th>

              <th>Status</th>

              <th>Submitted</th>

              <th>Verified</th>

              <th>Action</th>

            </tr>

          </thead>

          <tbody>

            {records.length === 0 ? (

              <tr>

                <td
                  colSpan="8"
                  className="no-records"
                >
                  No KYC Records Found
                </td>

              </tr>

            ) : (

              records.map((record) => (

                <tr key={record.id}>

                  <td>{record.id}</td>

                  <td>{record.customerId}</td>

                  <td>{record.panNumber}</td>

                  <td>{record.aadhaarNumber}</td>

                  <td>

                    <span
                      className={`status ${record.status.toLowerCase()}`}
                    >
                      {record.status}
                    </span>

                  </td>

                  <td>

                    {record.submittedAt
                      ? new Date(record.submittedAt).toLocaleDateString()
                      : "-"}

                  </td>

                  <td>

                    {record.verifiedAt
                      ? new Date(record.verifiedAt).toLocaleDateString()
                      : "-"}

                  </td>

                  <td>

                    <div className="action-buttons">

                      {/* View */}

                      <button
                        className="view-btn"
                        title="View Details"
                        onClick={() =>
                          setSelectedKyc(record)
                        }
                      >
                        <FaEye />
                      </button>

                      {/* Pending Actions */}

                      {record.status === "PENDING" ? (

                        <>

                          <button
                            className="verify-btn"
                            onClick={() =>
                              handleVerify(record.id)
                            }
                          >
                            Verify
                          </button>

                          <button
                            className="reject-btn"
                            onClick={() =>
                              handleReject(record.id)
                            }
                          >
                            Reject
                          </button>

                        </>

                      ) : (

                        <span className="completed">
                          Completed
                        </span>

                      )}

                    </div>

                  </td>

                </tr>

              ))

            )}

          </tbody>

        </table>

      </div>

      {/* Pagination */}

      {records.length > 0 && (

        <div className="pagination-container">

          <span className="pagination-info">

            Showing{" "}

            {(currentPage - 1) * recordsPerPage + 1}

            -

            {Math.min(
              currentPage * recordsPerPage,
              records.length
            )}

            {" "}of {records.length}

          </span>

          <div className="pagination-buttons">

            <button
              className="pagination-btn"
              disabled={currentPage === 1}
              onClick={() =>
                setCurrentPage(currentPage - 1)
              }
            >
              Previous
            </button>

            <span className="page-number">

              Page {currentPage} of {totalPages}

            </span>

            <button
              className="pagination-btn"
              disabled={
                currentPage === totalPages
              }
              onClick={() =>
                setCurrentPage(currentPage + 1)
              }
            >
              Next
            </button>

          </div>

        </div>

      )}

    </div>

  );

}

export default KYCTable;