window.onload = () => {
  const form = document.getElementById("noteForm");
  const noteTitle = document.getElementById("noteTitle");
  const noteContent = document.getElementById("noteContent");
  const noteList = document.getElementById("noteList");
  const importInput = document.createElement("input");
  importInput.type = "file";
  importInput.accept = ".json";
  importInput.style.display = "none";

  const exportBtn = document.getElementById("exportBtn");
  const importBtn = document.getElementById("importBtn");

  const action = document.getElementById("action");

  const editNote = "Edit note";
  const createNote = "Create note";

  let currentNote = undefined;

  exportBtn.addEventListener("click", () => {
    const notes = getItemsFromLocalStorage();
    const blob = new Blob([JSON.stringify(notes, null, 2)], {
      type: "application/json",
    });
    const url = URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    a.download = "notes_export.json";
    a.click();
  });

  importBtn.addEventListener("click", () => {
    importInput.click();
  });

  importInput.addEventListener("change", (e) => {
    const file = e.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (event) => {
        try {
          const importedNotes = JSON.parse(event.target.result);

          Object.keys(localStorage).forEach((key) => {
            localStorage.removeItem(key);
          });

          importedNotes.forEach((note) => addNoteToLocalStorage(note));

          render();

          alert("Notes imported successfully!");
        } catch (error) {
          alert("Error importing notes. Please check the file format.");
        }
      };
      reader.readAsText(file);
    }
  });

  form.addEventListener("submit", (e) => {
    if (currentNote === undefined)
      addNoteToLocalStorage(createNewNote(noteTitle.value, noteContent.value));
    else {
      currentNote.title = noteTitle.value;
      currentNote.content = noteContent.value;
      addNoteToLocalStorage(currentNote);
      currentNote = undefined;
    }

    render();
    // e.preventDefault();
  });

  const getNote = (e, note) => {
    currentNote = note;
    noteTitle.value = note.title;
    noteContent.value = note.content;
    render();
  };

  const addNoteToSidePanel = (note) => {
    li = document.createElement("li");
    li.innerHTML = note.title;
    li.onclick = (e) => getNote(e, note);
    noteList.appendChild(li);
  };

  const createNewNote = (title, content) => {
    return {
      id: crypto.randomUUID(),
      title: title,
      content: content,
      date: Date.now(),
    };
  };

  const addNoteToLocalStorage = (note) => {
    localStorage.setItem(
      note.id,
      JSON.stringify({
        id: note.id,
        title: note.title,
        content: note.content,
        date: note.date,
      })
    );
  };

  const getItemsFromLocalStorage = () => {
    const items = [];
    for (i = 0; i < localStorage.length; i++) {
      items.push(JSON.parse(localStorage.getItem(localStorage.key(i))));
    }
    return items.sort((a, b) => {
      return a.date < b.date;
    });
  };

  const currentAction = () => {
    if (currentNote !== undefined) {
      return editNote;
    }
    return createNote;
  };

  const render = () => {
    noteList.innerHTML = "";
    notes = getItemsFromLocalStorage();
    action.innerHTML = currentAction();
    notes.forEach((el) => addNoteToSidePanel(el));
  };

  render();
};
