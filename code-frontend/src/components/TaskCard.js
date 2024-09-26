import React, { useState, useRef, useEffect } from 'react';
import axios from 'axios';
import favoriteIcon from '../images/favorite.svg';
import vector2Icon from '../images/vector2.svg';
import frameIcon from '../images/frame1.svg';
import colorIcon from '../images/group2442.svg';
import vectorIcon from '../images/vector1.svg';
import ColorPicker from './ColorPicker';
import '../scss/TaskCard.scss'; 

const TaskCard = ({ id, title: initialTitle, description: initialDescription, initialFavorite, initialColor, onDelete, onColorChange,onFavoriteChange, }) => {
  const [color, setColor] = useState(initialColor || '#DAFF8B');
  const [showColorPicker, setShowColorPicker] = useState(false);
  const [isEditing, setIsEditing] = useState(false);
  const [title, setTitle] = useState(initialTitle);
  const [description, setDescription] = useState(initialDescription);
  const [favorite, setFavorite] = useState(initialFavorite);
  const [isColorActive, setIsColorActive] = useState(false);
  const cardRef = useRef(null);

  const handleColorSelect = async (selectedColor) => {
    setColor(selectedColor);
    setShowColorPicker(false);
    await updateColor(selectedColor);
    onColorChange(id, selectedColor);
  };

  const updateColor = async (selectedColor) => {
    try {
      await axios.patch(`http://localhost:8080/api/tasks/${id}/color`, { color: selectedColor }, {
        headers: { 'Content-Type': 'application/json' }
      });
      console.log('Color updated successfully');
    } catch (error) {
      console.error('Error updating color:', error);
    }
  };

  const handleDelete = async () => {
    try {
      await axios.delete(`http://localhost:8080/api/tasks/${id}`);
      console.log(`Task "${title}" deleted`);
      if (onDelete) onDelete(id);
    } catch (error) {
      console.error('Error deleting task:', error);
    }
  };

  const toggleFavorite = () => {
    setFavorite(prev => !prev);
    const newFavoriteStatus = !favorite;
    setFavorite(newFavoriteStatus);
    onFavoriteChange(id, newFavoriteStatus);
  };

  const saveChanges = async () => {
    try {
      await axios.put(`http://localhost:8080/api/tasks/${id}`, {
        title,
        description,
        favorite,
        color,
      });
      console.log('Task updated successfully');
    } catch (error) {
      console.error('Error updating task:', error);
    }
  };

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (cardRef.current && !cardRef.current.contains(event.target)) {
        saveChanges();
        setIsEditing(false);
        setIsColorActive(false);
      }
    };

    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, [title, description, favorite, color]);

  return (
    <div
      ref={cardRef}
      className="task-card" 
      style={{ backgroundColor: color }}
    >
      <div className="task-card__content">
        <div className="task-card__header">
          {isEditing ? (
            <input
              type="text"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              className="task-card__title"
            />
          ) : (
            <h2 className="task-card__title">{title}</h2>
          )}
          <img
            src={favorite ? favoriteIcon : vector2Icon}
            alt="Favorito"
            className={`task-card__favorite ${isEditing ? '' : 'opacity-50 cursor-not-allowed'}`}
            onClick={isEditing ? toggleFavorite : null}
          />
        </div>
        <div className="task-card__divider"></div>
        <div className="task-card__description">
          {isEditing ? (
            <input
              type="text"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              className="task-card__desc-input"
            />
          ) : (
            <p className="task-card__desc">{description}</p>
          )}
        </div>
      </div>

      <div className="task-card__footer">
        <div className="task-card__actions">
          <img
            src={frameIcon}
            alt="Editar"
            className="task-card__edit-icon"
            onClick={() => setIsEditing(!isEditing)}
          />
          <div
            className={`task-card__color-picker ${isColorActive ? 'active' : ''}`}
            onClick={() => {
              setIsColorActive(!isColorActive);
              setShowColorPicker(!showColorPicker);
            }}
          >
            <img
              src={colorIcon}
              alt="Color"
              className="task-card__color-icon"
            />
            {showColorPicker && (
              <ColorPicker onSelectColor={handleColorSelect} />
            )}
          </div>
        </div>

        <img
          src={vectorIcon}
          alt={isEditing ? "Cancelar" : "Excluir"}
          className="task-card__delete-icon"
          onClick={isEditing ? () => setIsEditing(false) : handleDelete}
        />
      </div>
    </div>
  );
};

export default TaskCard;