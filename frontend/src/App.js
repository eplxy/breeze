import React, {useState} from "react";
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import About from "./pages/About.js";
import Header from "./components/Header.jsx";

function App() {
  const [data, setData] = useState(null);

  React.useEffect(() => {
    fetch("/api")
      .then((res) => res.json())
      .then((data) => setData(data.message));
  }, []);

  return (
    <Router>
    <div className="Main">
    
      <Header />
      
        <h1>{!data ? "Loading..." : data}</h1>
        
      <Routes>
        <Route path="/about" element={<About />} /> {/* Add this line */}
      </Routes>
    </div>
  </Router>
  );
}

export default App;