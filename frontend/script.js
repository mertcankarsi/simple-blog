document.addEventListener('DOMContentLoaded', () => {
    const input = document.querySelector('.prompt input');
    const output = document.querySelector('.output');

    const commands = {
        help: () => {
            return "Available commands: help, clear, about";
        },
        clear: () => {
            output.innerHTML = '';
            return '';
        },
        about: () => {
            return "DOSBlog v1.0 - A simple blogging platform.";
        }
    };

    input.addEventListener('keydown', (event) => {
        if (event.key === 'Enter') {
            const command = input.value.trim();
            input.value = '';

            if (commands[command]) {
                output.innerHTML += `<div>${commands[command]()}</div>`;
            } else {
                output.innerHTML += `<div>Unknown command: ${command}</div>`;
            }

            output.scrollTop = output.scrollHeight; // Scroll to the bottom
        }
    });
});