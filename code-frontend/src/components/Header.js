import React from 'react';
import logo from '../images/image8.svg'; 
import searchIcon from '../images/search.svg'; 
import vectorIcon from '../images/vector1.svg'; 
import '../scss/Header.scss'; 

const Header = ({ searchTerm, setSearchTerm }) => {
  return (
    <header className="header">
      <div className="logo-container">
        <img 
          src={logo} 
          alt="Logo" 
          className="logo" 
        />
        <h1 className="title">
          CoreNotes
        </h1>
        
        <div className="search-container">
          <input
            type="text"
            placeholder="Pesquisar notas"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)} 
            className="search-input"
          />
          <div className="search-icon">
            <img 
              src={searchIcon} 
              alt="Search" 
            />
          </div>
        </div>
      </div>

      <div className="icon-container">
        <img 
          src={vectorIcon} 
          alt="Vector" 
          className="vector-icon" 
        />
      </div>
    </header>
  );
};

export default Header;
