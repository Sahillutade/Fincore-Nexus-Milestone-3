import { BrowserRouter, Routes, Route } from "react-router-dom";  


import Dashboard from "./pages/Dashboard/Dashboard";

import Accounts from "./pages/Accounts/Accounts";
import Customers from "./pages/Customers/Customers";
import Transactions from "./pages/Transactions/Transactions";
import Loans from "./pages/Loans/Loans";
import KYC from "./pages/KYC/KYC";
import Settings from "./pages/Settings/Settings";
import NotFound from "./pages/NotFound/NotFound";
import Payments from "./pages/Payments/Payments";
import NewTransfer from "./pages/Payments/NewTransfer";
import Beneficiaries from "./pages/Payments/Beneficiaries";


import AddCustomer from "./pages/AddCustomer/AddCustomer";
import CreateAccount from "./pages/CreateAccount/CreateAccount";

function App() {
  return (
    <BrowserRouter>
      <Routes>

        <Route path="/" element={<Dashboard />} />
        <Route path="/dashboard" element={<Dashboard />} />
        {/* Dashboard */}
        <Route path="/dashboard" element={<Dashboard />} />


        {/* Customer */}
        <Route path="/customers" element={<Customers />} />
        <Route path="/add-customer" element={<AddCustomer />} />

        {/* Account */}
        <Route path="/accounts" element={<Accounts />} />
        <Route path="/create-account" element={<CreateAccount />} />

        {/* Transactions */}
        <Route path="/transactions" element={<Transactions />} />

        {/* Loans */}
        <Route path="/loans" element={<Loans />} />

        {/* Payments */}
        <Route path="/payments" element={<Payments />} />

        <Route path="/payments/new" element={<NewTransfer />} />

        <Route path="/payments/beneficiaries" element={<Beneficiaries />} />

        {/* KYC */}
        <Route path="/kyc" element={<KYC />} />

        {/* Settings */}
        <Route path="/settings" element={<Settings />} />


        {/* 404 */}
        <Route path="*" element={<NotFound />} />

      </Routes>
    </BrowserRouter>
  );
}

export default App;