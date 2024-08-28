import subprocess
import os

def generate_png_from_puml(puml_file):
    # Generate PNG file from .puml file using PlantUML
    subprocess.run(['java', '-jar', 'plantuml-1.2024.6.jar', puml_file], check=True)

def update_readme_with_diagram(diagram_type, puml_file, png_file):
    # Generate PNG file
    parent_puml_file_path = os.path.join('..', puml_file)
    generate_png_from_puml(parent_puml_file_path)

    # Path to the README.md file in the parent directory
    readme_path = os.path.join('..', 'README.md')

    # Update the README
    with open(readme_path, 'r') as readme:
        content = readme.readlines()

    # Find the section to update
    start_marker = f"## {diagram_type} Diagram\n"
    end_marker = "\n## " if diagram_type == "Class" else "\nThat's it!"
    
    try:
        start_index = content.index(start_marker) + 1
    except ValueError:
        # If start_marker is not found, append the new section at the end
        content.append(f"\n{start_marker}\n![{diagram_type} Diagram]({png_file})\n")
    else:
        try:
            end_index = content.index(end_marker, start_index)
        except ValueError:
            # If end_marker is not found, update till the end of the file
            end_index = len(content)
        
        # Update the section with the new diagram PNG reference
        content[start_index:end_index] = [f"\n![{diagram_type} Diagram]({png_file})\n"]

    # Write the updated content back to the README
    with open(readme_path, 'w') as readme:
        readme.writelines(content)

# Update README with Class Diagram
update_readme_with_diagram("Class", "class-diagram.puml", "class-diagram.png")

# Update README with Sequence Diagram
update_readme_with_diagram("Sequence", "sequence-diagram.puml", "sequence-diagram.png")