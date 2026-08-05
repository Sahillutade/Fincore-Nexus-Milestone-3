import { useEffect, useMemo, useState } from "react";
import MainLayout from "../../layouts/MainLayout";
import "./KYC.css";

import KYCCards from "./KYCCards";
import KYCTable from "./KYCTable";
import KYCModal from "./KYCModal";

import {
  getAllKyc,
  verifyKyc,
  rejectKyc,
} from "../../services/kycService";

function KYC() {

  const [kycRecords, setKycRecords] = useState([]);
  const [loading, setLoading] = useState(true);

  const [search, setSearch] = useState("");
  const [statusFilter, setStatusFilter] = useState("ALL");

  const [selectedKyc, setSelectedKyc] = useState(null);

  const [currentPage, setCurrentPage] = useState(1);

  const recordsPerPage = 5;

  useEffect(() => {
    loadKyc();
  }, []);

  const loadKyc = async () => {

    try {

      setLoading(true);

      const response = await getAllKyc();

      setKycRecords(response.data || []);

    } catch (error) {

      console.error(error);

    } finally {

      setLoading(false);

    }

  };

  const handleVerify = async (id) => {

    try {

      await verifyKyc(id);

      loadKyc();

      setSelectedKyc(null);

    } catch (error) {

      console.error(error);

    }

  };

  const handleReject = async (id) => {

    try {

      await rejectKyc(id);

      loadKyc();

      setSelectedKyc(null);

    } catch (error) {

      console.error(error);

    }

  };

  const filteredRecords = useMemo(() => {

    return kycRecords.filter((record) => {

      const searchMatch =
        (
          record.customerId +
          record.panNumber +
          record.aadhaarNumber
        )
          .toLowerCase()
          .includes(search.toLowerCase());

      const statusMatch =
        statusFilter === "ALL" ||
        record.status === statusFilter;

      return searchMatch && statusMatch;

    });

  }, [kycRecords, search, statusFilter]);

  useEffect(() => {

    setCurrentPage(1);

  }, [search, statusFilter]);

  const totalPages = Math.max(
    1,
    Math.ceil(filteredRecords.length / recordsPerPage)
  );

  const currentRecords = filteredRecords.slice(
    (currentPage - 1) * recordsPerPage,
    currentPage * recordsPerPage
  );

  return (

    <MainLayout>

      <div className="kyc-page">

        <div className="kyc-header">

          <div>

            <h1>KYC Management</h1>

            <p>
              Manage customer verification documents.
            </p>

          </div>

          <button
            className="refresh-btn"
            onClick={loadKyc}
          >
            Refresh
          </button>

        </div>

        <KYCCards
          total={kycRecords.length}
          verified={
            kycRecords.filter(
              k => k.status === "VERIFIED"
            ).length
          }
          pending={
            kycRecords.filter(
              k => k.status === "PENDING"
            ).length
          }
          rejected={
            kycRecords.filter(
              k => k.status === "REJECTED"
            ).length
          }
        />

        {loading ? (

          <div className="loading">
            Loading...
          </div>

        ) : (

          <KYCTable
            records={currentRecords}
            search={search}
            setSearch={setSearch}
            statusFilter={statusFilter}
            setStatusFilter={setStatusFilter}
            currentPage={currentPage}
            totalPages={totalPages}
            setCurrentPage={setCurrentPage}
            recordsPerPage={recordsPerPage}
            setSelectedKyc={setSelectedKyc}
            handleVerify={handleVerify}
            handleReject={handleReject}
            records={currentRecords}
            totalRecords={filteredRecords.length}
          />

        )}

        <KYCModal
          selectedKyc={selectedKyc}
          setSelectedKyc={setSelectedKyc}
          handleVerify={handleVerify}
          handleReject={handleReject}
        />

      </div>

    </MainLayout>

  );

}

export default KYC;