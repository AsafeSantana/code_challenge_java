import React from 'react';
import Header from './components/Header';
import TaskList from './components/TaskList';
import TaskForm from './components/TaskForm';

class App extends React.Component {
  state = {
    tasks: [],
    searchTerm: '',
  };

  addTask = (newTask) => {
    const taskWithId = { ...newTask, id: Date.now() };
    this.setState((prevState) => ({
      tasks: [...prevState.tasks, taskWithId],
    }));
  };

  setSearchTerm = (term) => {
    this.setState({ searchTerm: term });
  };

  render() {
    return (
      <div className="min-h-screen bg-gray-100">
        <Header 
          searchTerm={this.state.searchTerm} 
          setSearchTerm={this.setSearchTerm} 
        />
        <div className="container mx-auto p-4">
          
          

          {/* Componente para listar as tarefas */}
          <TaskList 
            tasks={this.state.tasks} 
            searchTerm={this.state.searchTerm} 
            onDeleteTask={this.handleDeleteTask} 
          />
        </div>
      </div>
    );
  }
}

export default App;
