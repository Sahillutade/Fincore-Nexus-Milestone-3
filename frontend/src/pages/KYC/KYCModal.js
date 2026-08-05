import "./KYCModal.css";

import {
  FaTimes,
  FaUser,
  FaIdCard,
  FaAddressCard,
  FaCalendarAlt,
} from "react-icons/fa";

function KYCModal({
  selectedKyc,
  setSelectedKyc,
  handleVerify,
  handleReject,
}) {
  if (!selectedKyc) return null;

  const closeModal = () => setSelectedKyc(null);

  return (
    <div
      className="kyc-popup-overlay"
      onClick={closeModal}
    >
      <div
        className="kyc-popup"
        onClick={(e) => e.stopPropagation()}
      >

        {/* Header */}
        <div className="kyc-popup-header">
          <h2>KYC Details</h2>

          <button
            type="button"
            className="kyc-popup-close"
            onClick={closeModal}
            title="Close"
          >
            <FaTimes />
          </button>
        </div>

        {/* Body */}
        <div className="kyc-popup-body">

          <div className="detail-row">
            <FaUser />

            <div>
              <label>Customer ID</label>
              <p>{selectedKyc.customerId || "-"}</p>
            </div>
          </div>

          <div className="detail-row">
            <FaIdCard />

            <div>
              <label>PAN Number</label>
              <p>{selectedKyc.panNumber || "-"}</p>
            </div>
          </div>

          <div className="detail-row">
            <FaAddressCard />

            <div>
              <label>Aadhaar Number</label>
              <p>{selectedKyc.aadhaarNumber || "-"}</p>
            </div>
          </div>

          <div className="detail-row">
            <FaCalendarAlt />

            <div>
              <label>Submitted On</label>

              <p>
                {selectedKyc.submittedAt
                  ? new Date(
                      selectedKyc.submittedAt
                    ).toLocaleDateString()
                  : "-"}
              </p>
            </div>
          </div>

          <div className="detail-row">
            <FaCalendarAlt />

            <div>
              <label>Verified On</label>

              <p>
                {selectedKyc.verifiedAt
                  ? new Date(
                      selectedKyc.verifiedAt
                    ).toLocaleDateString()
                  : "-"}
              </p>
            </div>
          </div>

          <div className="detail-row">
            <FaIdCard />

            <div>
              <label>Status</label>

              <span
                className={`kyc-modal-status ${selectedKyc.status?.toLowerCase()}`}
              >
                {selectedKyc.status || "-"}
              </span>
            </div>
          </div>

        </div>

        {/* Footer */}
        <div className="kyc-popup-footer">

          {selectedKyc.status === "PENDING" && (
            <>
              <button
                type="button"
                className="kyc-modal-verify"
                onClick={() =>
                  handleVerify(selectedKyc.id)
                }
              >
                Verify
              </button>

              <button
                type="button"
                className="kyc-modal-reject"
                onClick={() =>
                  handleReject(selectedKyc.id)
                }
              >
                Reject
              </button>
            </>
          )}

          <button
            type="button"
            className="kyc-popup-close-btn"
            onClick={closeModal}
          >
            Close
          </button>

        </div>

      </div>
    </div>
  );
}

export default KYCModal;