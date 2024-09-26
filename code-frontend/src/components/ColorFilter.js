import React from 'react';
import Draggable from 'react-draggable';
import '../scss/ColorFilter.scss'; // Importa o arquivo Sass

const ColorFilter = ({ onSelectColor }) => {
  const colors = [
    '#BAE2FF', '#B9FFDD', '#FFE8AC', '#FFCAB9', 
    '#F99494', '#9DD6FF', '#ECA1FF', '#DAFF8B', 
    '#FFA285', '#CDCDCD', '#979797', '#A99A7C',
  ];

  const handleRemoveFilter = () => {
    onSelectColor(''); // Passa uma string vazia para remover o filtro
  };

  return (
    <Draggable>
      <div className="color-filter">
        <button
          onClick={handleRemoveFilter}
          className="remove-filter-btn"
        >
          Sem Filtro
        </button>
        <div className="color-options">
          {colors.map((color) => (
            <div
              key={color}
              onClick={() => onSelectColor(color)}
              className="color-circle"
              style={{ backgroundColor: color }}
            />
          ))}
        </div>
      </div>
    </Draggable>
  );
};

export default ColorFilter;
