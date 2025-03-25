# Branches 

## Create a new branch
```bash
git branch <branch-name>
```

## Switch to a branch
```bash
git checkout <branch-name>
```

## Create a new branch and switch to it
```bash
git checkout -b <branch-name>
```

## List all branches
```bash
git branch
```

## set the upstream branch
```bash
git push --set-upstream origin <branch-name>
```

## Delete a branch
```bash
git branch -d <branch-name>
```

## Delete a branch (force)
```bash
git branch -D <branch-name>
```

## Merge a branch
```bash
git merge <branch-name>
```
# Commits

## Add all files to the staging area
```bash
git add .
```
## Add a specific file to the staging area
```bash
git add <file-name>
```

## Commit changes
```bash
git commit -m "commit message"
```
## voir la liste des fichiers modifiés
```bash
git status
```

## Push changes to a remote repository



```bash
git push
```
## Pull changes from a remote repository
```bash
git pull
```


## merge branch to master
```bash
git checkout master
git merge <branch-name>
```

