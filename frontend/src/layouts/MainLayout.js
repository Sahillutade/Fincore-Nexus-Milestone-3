import Navbar from "../components/Navbar/Navbar";
import Sidebar from "../components/Sidebar/Sidebar";

function MainLayout({ children }) {
  return (
    <div style={{ display: "flex", flexDirection: "column", height: "100vh" }}>
      <Navbar />

      <div style={{ display: "flex", flex: 1 }}>
        <Sidebar />

        <div
          style={{
            flex: 1,
            padding: "20px",
            backgroundColor: "#f4f6f9"
          }}
        >
          {children}
        </div>
      </div>
    </div>
  );
}

export default MainLayout;