import "./Dashboard.css";

import { useEffect, useState } from "react";

import MainLayout from "../../layouts/MainLayout";

// Components
import DashboardCard from "../../components/DashboardCard/DashboardCard";
import TransactionChart from "../../components/Charts/TransactionChart";
import QuickActions from "../../components/QuickActions/QuickActions";
import RecentActivity from "../../components/RecentActivity/RecentActivity";
import CustomerTable from "../../components/CustomerTable/CustomerTable";
import TransactionTable from "../../components/TransactionTable/TransactionTable";

// Services
import { getAllCustomers } from "../../services/customerService";
import { getAllAccounts } from "../../services/accountService";
import { getAllTransactions } from "../../services/transactionService";

// Icons
import {  
  FaUsers,
  FaUniversity,
  FaExchangeAlt,
  FaHandHoldingUsd,
} from "react-icons/fa";

function Dashboard() {

  const [stats, setStats] = useState({
    customers: 0,
    accounts: 0,
    transactions: 0,
    loans: 350,
  });

  useEffect(() => {

    async function loadDashboard() {

      try {

        const [
          customers,
          accounts,
          transactions,
        ] = await Promise.all([
          getAllCustomers(),
          getAllAccounts(),
          getAllTransactions(),
        ]);

        setStats({
          customers: customers.length,
          accounts: accounts.length,
          transactions: transactions.length,
          loans: 350,
        });

      } catch (error) {

        console.error("Error loading dashboard:", error);

      }

    }

    loadDashboard();

  }, []);

  return (

    <MainLayout>

      <div className="dashboard">

        {/* Dashboard Title */}

        <h1 className="dashboard-title">

          Banking Dashboard

        </h1>

        {/* Welcome Card */}

        <div className="welcome-card">

          <div>

            <h2>Welcome to FinCore Nexus</h2>

            <p>

              Enterprise Digital Banking Platform

            </p>

          </div>

          <div>

            <h1>₹12.4M</h1>

            <p>Total Daily Transactions</p>

          </div>

        </div>

        {/* Statistics Cards */}

        <div className="card-container">

          <DashboardCard
            title="Customers"
            value={stats.customers}
            color="#2563EB"
            icon={<FaUsers />}
            trend="+8%"
          />

          <DashboardCard
            title="Accounts"
            value={stats.accounts}
            color="#16A34A"
            icon={<FaUniversity />}
            trend="+5%"
          />

          <DashboardCard
            title="Transactions"
            value={stats.transactions}
            color="#F59E0B"
            icon={<FaExchangeAlt />}
            trend="+18%"
          />

          <DashboardCard
            title="Loans"
            value={stats.loans}
            color="#DC2626"
            icon={<FaHandHoldingUsd />}
            trend="+3%"
          />

        </div>

        

        {/* Quick Actions */}

        <QuickActions />

        {/* Monthly Transaction Chart */}

        <div className="table-card chart-section">

          <div className="section-header">

            <h2>Monthly Transactions</h2>

          </div>

          <TransactionChart />

        </div>

        {/* Tables */}

        <div className="table-container">

          {/* Recent Transactions */}

          <div className="table-card">

            <div className="section-header">

              <h2>Recent Transactions</h2>

            </div>

            <TransactionTable />

          </div>

          {/* Recent Customers */}

          <div className="table-card">

            <div className="section-header">

              <h2>Recent Customers</h2>

            </div>

            <CustomerTable />

          </div>

        </div>

        {/* Recent Activity */}

        <div className="activity-section">

          <div className="section-header">

            <h2>Recent Activity</h2>

          </div>

          <RecentActivity />

        </div>

      </div>

    </MainLayout>

  );

}

export default Dashboard;